package moneyexchange.Trading_Exchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import moneyexchange.CurrencyName;

public class TradingClient {
    private String name;
    private String username;
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public TradingClient(String name, String username) {
        this.name = name;
        this.username = username;
    }

    // Send the created order to server
    public void addOrder(Order order) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            out.println("ADD_ORDER:" + order.toString());
            String response = in.readLine();  // Read server response to ensure proper communication
            System.out.println("Server response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Return a list of orders (got it from server)
    public List<Order> getOrders(String type) {
        List<Order> orders = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("GET_" + type.toUpperCase() + "_ORDERS");
            String response;
            while ((response = in.readLine()) != null && !response.equals("END")) {
                orders.add(Order.fromString(response));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order createOrder(Order.Command command, double sourcePrice, CurrencyName targetCurrency, int amount) {
        Order newOrder =  new Order(command, sourcePrice, targetCurrency, amount);
        return newOrder;
    }
}
