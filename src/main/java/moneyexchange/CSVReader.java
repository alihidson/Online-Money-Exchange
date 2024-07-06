package moneyexchange;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<List<CurrencyInfo>> readCSV(String filePath) {
        List<List<CurrencyInfo>> prices = new ArrayList<>();
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) {
                    List<CurrencyInfo> linePrices = new ArrayList<>();

                    linePrices.add(new CurrencyInfo("USD", data[2], data[0], data[1]));
                    linePrices.add(new CurrencyInfo("EUR", data[3], data[0], data[1]));
                    linePrices.add(new CurrencyInfo("TOMAN", data[4], data[0], data[1]));
                    linePrices.add(new CurrencyInfo("YEN", data[5], data[0], data[1]));
                    linePrices.add(new CurrencyInfo("GBP", data[6], data[0], data[1]));
                    prices.add(linePrices);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prices;
    }
}

