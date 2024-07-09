package moneyexchange.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import moneyexchange.Trading_Exchange.Order;
import moneyexchange.Trading_Exchange.OrderProcessor;

public class TradingServer {
    private static final int SERVER_PORT = 8585;
    public static OrderProcessor orderProcessor = new OrderProcessor();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is listening on port " + SERVER_PORT);

            while (true) {
                new ClientHandler(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("ADD_ORDER:")) {
                        Order order = Order.fromString(message.substring(10));
                        orderProcessor.addOrder(order);
                        out.println("Order added successfully.");
                    } else if (message.equals("GET_BUY_ORDERS")) {
                        for (Order order : orderProcessor.getBuyOrders()) {
                            System.out.println(order);
                            out.println(order.toString());
                        }
                        out.println("END");
                    } else if (message.equals("GET_SELL_ORDERS")) {
                        for (Order order : orderProcessor.getSellOrders()) {
                            System.out.println(order);
                            out.println(order.toString());
                        }
                        out.println("END");
                    } else {
                        out.println("Unknown command.");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
