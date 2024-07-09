package moneyexchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CSVToArray {
    private String[][] dataArray;
    private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public CSVToArray(String csvFilePath) {
        String line;
        String cvsSplitBy = "\\s{2,}"; // Use two or more spaces as delimiter

        List<String[]> data = new ArrayList<>();

        try (InputStream inputStream = getClass().getResourceAsStream(csvFilePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            while ((line = br.readLine()) != null) {
                // Use \\s{2,} to split the line by two or more spaces
                String[] row = line.trim().split(cvsSplitBy);

                // Split the date and time in the first element if applicable
                if (row.length > 0 && row[0].contains(" ")) {
                    String[] dateTimeParts = row[0].split(" ");
                    if (dateTimeParts.length == 2) {
                        String[] newRow = new String[row.length + 1];
                        newRow[0] = dateTimeParts[0]; // date part
                        newRow[1] = removeMilliseconds(dateTimeParts[1]); // time part without milliseconds
                        System.arraycopy(row, 1, newRow, 2, row.length - 1);
                        row = newRow;
                    }
                }

                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convert List to 2D array
        dataArray = new String[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            dataArray[i] = data.get(i);
        }
    }

    private Duration getMinDuration(TimePeriod period) {
        switch (period) {
            case THIRTY_MINUTES:
                return Duration.ofMinutes(30);
            case ONE_HOUR:
                return Duration.ofHours(1);
            case THREE_HOURS:
                return Duration.ofHours(3);
            case SIX_HOURS:
                return Duration.ofHours(6);
            case TWELVE_HOURS:
            default:
                return Duration.ofMinutes(1); // Default to no filtering
        }
    }

    public String[][] getDataArray(TimePeriod period) {
        List<String[]> filteredDataList = new ArrayList<>();

        LocalTime lastTime = null;
        LocalTime time;
        Duration minDuration = getMinDuration(period);

        // Add the header row
        if (dataArray.length > 0) {
            filteredDataList.add(dataArray[0]);
        }

        for (int i = 1; i < dataArray.length; i++) {
            // Skip empty rows
            if (dataArray[i].length == 0) {
                continue;
            }

            // Ensure the row has enough columns
            if (dataArray[i].length > 1) {
                try {
                    String timeString = dataArray[i][1]; // Time is now in the second column
                    time = LocalTime.parse(timeString, formatter);

                    if (lastTime == null || Duration.between(lastTime, time).compareTo(minDuration) >= 0) {
                        filteredDataList.add(dataArray[i]);
                        lastTime = time;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Error parsing time1: " + dataArray[i][1]);
                    e.printStackTrace();
                }
            }
        }

        // Convert List to 2D array
        return filteredDataList.toArray(new String[0][0]);
    }

    private String removeMilliseconds(String time) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(time, inputFormatter);
        return localTime.format(outputFormatter);
    }

    public String[] getLineByIndex(int i){
        return dataArray[i];
    }
}
