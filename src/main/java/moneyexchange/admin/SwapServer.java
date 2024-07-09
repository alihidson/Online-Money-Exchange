package moneyexchange.admin;

import moneyexchange.CSVToArray;
import moneyexchange.CurrencyName;
import moneyexchange.TimeIndexFinder;
import moneyexchange.Trading_Swap.SwapRequest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SwapServer {
    private static final int PORT = 1000;
    private Map<CurrencyName, Double> exchangeRates;
    public TimeIndexFinder finder = new TimeIndexFinder();
    private CSVToArray data = new CSVToArray("/data/currency_prices.csv");

    public SwapServer() {
        exchangeRates = new HashMap<>();
        initializeExchangeRates();
    }

    private void initializeExchangeRates() {
        // Initialize with some dummy exchange rates
        exchangeRates.put(CurrencyName.USD, 1.0);
        exchangeRates.put(CurrencyName.EUR, 0.9);
        exchangeRates.put(CurrencyName.TOMAN, 42.0);
        exchangeRates.put(CurrencyName.YEN, 110.0);
        exchangeRates.put(CurrencyName.GBP, 0.8);
    }

    public void updateExchangeRates() {
        String[] str = data.getLineByIndex(finder.getCurrentIndex());

        // Check if the line is valid and has the expected number of columns
        if (str != null && str.length >= 7) {
            System.out.println("Read line: " + String.join(",", str));
            exchangeRates.put(CurrencyName.USD, Double.parseDouble(str[2]));
            exchangeRates.put(CurrencyName.EUR, Double.parseDouble(str[3]));
            exchangeRates.put(CurrencyName.TOMAN, Double.parseDouble(str[4]));
            exchangeRates.put(CurrencyName.YEN, Double.parseDouble(str[5]));
            exchangeRates.put(CurrencyName.GBP, Double.parseDouble(str[6]));
        } else {
            System.err.println("Invalid line format: " + (str != null ? String.join(",", str) : "null line"));
        }
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("SwapServer started on port " + PORT);
            while (true) {
                new ClientHandler(serverSocket.accept(), this).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private SwapServer server;

        public ClientHandler(Socket socket, SwapServer server) {
            this.clientSocket = socket;
            this.server = server;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                SwapRequest request = (SwapRequest) in.readObject();
                double exchangeRate = calculatePairExchangeRate(request.getSourceCurr(), request.getTargetCurr());
                request.setExchangeRate(exchangeRate);
                out.writeObject(request);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        private double calculatePairExchangeRate(CurrencyName source, CurrencyName target) {
            server.updateExchangeRates();
            return server.exchangeRates.get(target) / server.exchangeRates.get(source);
        }
    }

    public static void main(String[] args) {
        new SwapServer().start();
    }
}
