package moneyexchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeIndexFinder {

    private int LENGTH;
    private String[][] STR;
    private CSVToArray data;
    private int INDEX = -1;  // -1 to indicate no valid index found
    private int currentIndex = 1;

    public TimeIndexFinder() {
        data = new CSVToArray("/data/currency_prices.csv");
        STR = data.getDataArray(TimePeriod.ONE_MINUTE);
        LENGTH = STR.length;
    }

    public int getCurrentIndex() {
        LocalTime now = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String nowFormatted = now.format(formatter);

        LocalTime fileTime;
        for (int i = currentIndex; i < LENGTH; i++) {
            if (STR[i].length > 0){
                try {
                    fileTime = LocalTime.parse(STR[i][1], formatter);
                    if (nowFormatted.equals(fileTime.format(formatter))) {
                        INDEX = i;
                        break; // Stop loop if match is found
                    }
                } catch (Exception e) {
                    // Handle parsing exceptions if any
                    System.out.println(i);
                    System.err.println("Error parsing time2: " + STR[i][1]);
                }
            }

        }
        return INDEX;
    }
}
