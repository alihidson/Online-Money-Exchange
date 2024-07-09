package moneyexchange.Trading_Exchange;

import java.util.HashMap;
import java.util.Map;

import moneyexchange.CurrencyName;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

public class TradingClientUI extends VBox {

    private TradingClient client;
    private VBox buyOrdersBox;
    private VBox sellOrdersBox;
    private Label orderDetailsLabel;

    private static final Map<String, String[]> CURRENCY_COLORS = new HashMap<>();

    static {
        CURRENCY_COLORS.put("USD", new String[]{"#3399FF", "#003366"}); // Light and dark blue for USD
        CURRENCY_COLORS.put("EUR", new String[]{"#66CC66", "#006633"}); // Light and dark green for EUR
        CURRENCY_COLORS.put("GBP", new String[]{"#FFCC66", "#CC9933"}); // Light and dark yellow for GBP
        CURRENCY_COLORS.put("TOMAN", new String[]{"#FFB74D", "#F57C00"}); // Light and dark orange for TOMAN
        CURRENCY_COLORS.put("YEN", new String[]{"#F06292", "#D81B60"}); // Light and dark pink for YEN
    }


    public TradingClientUI(String name, String username, CurrencyName srcCurrency) {
        client = new TradingClient(name, username);

        this.setSpacing(10);
        this.setStyle("-fx-padding: 10; -fx-background-color: #E0F7FA; -fx-border-color: #00838F; -fx-border-width: 2px;");

        // show buy orders
        Label buyLabel = new Label("Buy Orders");
        buyLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #003366;");
        buyOrdersBox = new VBox(4);
        buyOrdersBox.setStyle("-fx-padding: 10; -fx-background-color: #B2EBF2;");
        ScrollPane buyScrollPane = new ScrollPane(buyOrdersBox);
        buyScrollPane.setFitToWidth(true);
        buyScrollPane.setPrefHeight(150);

        // show sell orders
        Label sellLabel = new Label("Sell Orders");
        sellLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #006633;");
        sellOrdersBox = new VBox(4);
        sellOrdersBox.setStyle("-fx-padding: 10; -fx-background-color: #B2EBF2;");
        ScrollPane sellScrollPane = new ScrollPane(sellOrdersBox);
        sellScrollPane.setFitToWidth(true);
        sellScrollPane.setPrefHeight(150);

        orderDetailsLabel = new Label();
        orderDetailsLabel.setStyle("-fx-padding: 10; -fx-background-color: #D7CCC8; -fx-border-color: #5D4037; -fx-border-width: 1px;");
        orderDetailsLabel.setVisible(false);

        Button buyButton = new Button("Add Buy Order");
        buyButton.setStyle("-fx-background-color: #003366; -fx-text-fill: white;");
        buyButton.setOnAction(e -> showOrderDialog(Order.Command.BUY, srcCurrency));

        Button sellButton = new Button("Add Sell Order");
        sellButton.setStyle("-fx-background-color: #006633; -fx-text-fill: white;");
        sellButton.setOnAction(e -> showOrderDialog(Order.Command.SELL, srcCurrency));

        Button refreshButton = new Button("Refresh");
        refreshButton.setStyle("-fx-background-color: #00796B; -fx-text-fill: white;");
        refreshButton.setOnAction(e -> refreshOrders());

        HBox buttonBox = new HBox(10, buyButton, sellButton, refreshButton);
        buttonBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(buyLabel, buyScrollPane, sellLabel, sellScrollPane, orderDetailsLabel, buttonBox);

        refreshOrders();
    }

    // CHECK LATER
    private void refreshOrders() {
        buyOrdersBox.getChildren().clear();
        sellOrdersBox.getChildren().clear();

        double maxBuyPrice = client.getOrders("BUY").stream().mapToDouble(Order::getSourcePrice).max().orElse(1);
        double maxSellPrice = client.getOrders("SELL").stream().mapToDouble(Order::getSourcePrice).max().orElse(1);

        for (Order order : client.getOrders("BUY")) {
            buyOrdersBox.getChildren().add(createOrderLabel(order, maxBuyPrice));
        }
        for (Order order : client.getOrders("SELL")) {
            sellOrdersBox.getChildren().add(createOrderLabel(order, maxSellPrice));
        }
    }

    private HBox createOrderLabel(Order order, double maxPrice) {
        String sourceCurrency = order.getSourceCurrencyString();
        String[] colors = CURRENCY_COLORS.getOrDefault(sourceCurrency, new String[]{"#FFFFFF", "#CCCCCC"}); // Default to white and gray if no specific color is found

        String lightColor = colors[0];
        String darkColor = colors[1];

        Label label = new Label(order.username + ": " + order.sourceCurrency + "->" + order.getTargetCurrency() + " => price: " + order.getSourcePrice() + " amount: " + order.getAmount());
        double widthRatio = order.getSourcePrice() / maxPrice;
        label.setStyle("-fx-background-color: linear-gradient(to right, " + lightColor + " " + (widthRatio * 100) + "%, " + darkColor + " " + (widthRatio * 100) + "%); -fx-text-fill: white; -fx-padding: 5px;");
        HBox hBox = new HBox(label);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(2, 0, 2, 0));

        hBox.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> showOrderDetails(order));
        hBox.addEventFilter(MouseEvent.MOUSE_EXITED, event -> hideOrderDetails());

        return hBox;
    }
    private void showOrderDetails(Order order) {
        orderDetailsLabel.setText("User: " + order.username + "\nAmount: "  + order.getAmount() + "\nSource currency: " + order.sourceCurrency + "\nTarget Currency: " + order.getTargetCurrency() +  "\nPrice: " + order.getSourcePrice());
        orderDetailsLabel.setVisible(true);
    }

    private void hideOrderDetails() {
        orderDetailsLabel.setVisible(false);
    }

    private void showOrderDialog(Order.Command command, CurrencyName srcCurrency) {
        Dialog<Order> dialog = new Dialog<>();
        dialog.setTitle("New Order");
        dialog.setHeaderText("Enter Order Details");

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();

        Label targetCurrencyLabel = new Label("Target Currency");
        TextField targetCurrencyField = new TextField();

        Label sourcePriceLabel = new Label("Price:");
        TextField sourcePriceField = new TextField();



        VBox dialogVbox = new VBox(10, amountLabel, amountField, targetCurrencyLabel, targetCurrencyField, sourcePriceLabel, sourcePriceField);
        dialogVbox.setPadding(new Insets(10));
        dialog.getDialogPane().setContent(dialogVbox);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                try {
                    if (sourcePriceField.getText().isEmpty() || amountField.getText().isEmpty() || targetCurrencyField.getText().isEmpty()) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Something is missing, please try again.");
                        alert.showAndWait();
                        return null;
                    }
                    double sourcePrice = Double.parseDouble(sourcePriceField.getText());
                    int amount = Integer.parseInt(amountField.getText());
                    CurrencyName targetCurrency = setCurrencyName(targetCurrencyField.getText());
                    Order order = client.createOrder(command, sourcePrice, targetCurrency, amount);

                    client.addOrder(order);
                    refreshOrders();
                    return order;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    public VBox getRoot() {
        return this;
    }

    private CurrencyName setCurrencyName(String str){
        CurrencyName name;
        switch (str) {
            case "USD":
                name = CurrencyName.USD;
                break;
            case "EUR":
                name = CurrencyName.EUR;
                break;
            case "TOMAN":
                name = CurrencyName.TOMAN;
                break;
            case "YEN":
                name = CurrencyName.YEN;
                break;
            case "GBP":
                name = CurrencyName.GBP;
                break;
            default:
                name = null;
        }
        return name;
    }
}
