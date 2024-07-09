package moneyexchange.Trading_Swap;

import java.io.IOException;
import moneyexchange.CurrencyName;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class SwapUI {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 1000;

    double exchangeRate;
    private ToggleGroup operationGroup = new ToggleGroup();
    private RadioButton buyButton = new RadioButton("Buy");
    private RadioButton sellButton = new RadioButton("Sell");

    private ComboBox<CurrencyName> sourceCurrencyBox = new ComboBox<>();
    private ComboBox<CurrencyName> targetCurrencyBox = new ComboBox<>();

    private TextField amountField = new TextField();
    private Label equalAmount = new Label();

    private Label exchangeRateLabel = new Label("1 USD : ? EUR");

    private Button confirmButton = new Button("Confirm");
    private Button cancelButton = new Button("Cancel");

    private VBox layout;

    public SwapUI() {
        createUI();
    }

    private void createUI() {
        buyButton.setToggleGroup(operationGroup);
        sellButton.setToggleGroup(operationGroup);
        buyButton.setSelected(true);

        // Styles for RadioButtons
        buyButton.setStyle("-fx-font-weight: bold; -fx-padding: 10px;");
        sellButton.setStyle("-fx-font-weight: bold; -fx-padding: 10px;");

        // Choose source and target currencies
        sourceCurrencyBox.getItems().addAll(CurrencyName.values());
        sourceCurrencyBox.setValue(CurrencyName.USD);
        sourceCurrencyBox.setStyle("-fx-padding: 5px; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");

        targetCurrencyBox.getItems().addAll(CurrencyName.values());
        targetCurrencyBox.setValue(CurrencyName.EUR);
        targetCurrencyBox.setStyle("-fx-padding: 5px; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");

        // Set amount
        amountField.setPromptText("Enter amount");
        amountField.setStyle("-fx-padding: 5px; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-background-color: #ffffff;");

        // Styles for Labels
        equalAmount.setStyle("-fx-padding: 10px; -fx-font-size: 14px;");
        exchangeRateLabel.setStyle("-fx-padding: 10px; -fx-font-size: 14px;");

        // Update exchange rate label based on selected currencies
        sourceCurrencyBox.setOnAction(event -> updateExchangeRateLabel(exchangeRateLabel, sourceCurrencyBox, targetCurrencyBox));
        targetCurrencyBox.setOnAction(event -> updateExchangeRateLabel(exchangeRateLabel, sourceCurrencyBox, targetCurrencyBox));

        // Update equal amount when the amount field is changed
        amountField.textProperty().addListener((observable, oldValue, newValue) -> updateEqualAmount());

        // Styles for Buttons
        confirmButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        confirmButton.setOnAction(event -> {
            try {
                handleConfirm();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-border-radius: 5px;");
        cancelButton.setOnAction(event -> {
            amountField.clear();
            equalAmount.setText("");
        });

        // VBox layout
        layout = new VBox(10, buyButton, sellButton, sourceCurrencyBox, targetCurrencyBox, amountField, equalAmount, exchangeRateLabel, confirmButton, cancelButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f0f0f0; -fx-font-size: 14px;");
    }

    private void updateExchangeRateLabel(Label label, ComboBox<CurrencyName> sourceCurrencyBox, ComboBox<CurrencyName> targetCurrencyBox) {
        CurrencyName source = sourceCurrencyBox.getValue();
        CurrencyName target = targetCurrencyBox.getValue();
        exchangeRate = determineExchangeRate(source, target); // Placeholder method
        label.setText("1 " + source + " : " + exchangeRate + " " + target);
        updateEqualAmount();
    }

    private void updateEqualAmount() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            equalAmount.setText("Equal Amount: " + (exchangeRate * amount));
        } catch (NumberFormatException e) {
            equalAmount.setText("Equal Amount: ");
        }
    }

    private double determineExchangeRate(CurrencyName source, CurrencyName target) {
        try {
            SwapClient client = new SwapClient(SERVER_ADDRESS, SERVER_PORT);
            SwapRequest request = new SwapRequest(source, target);
            SwapRequest response = client.sendSwapRequest(request);
            return response.getExchangeRate();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return 1.0; // Fallback exchange rate
        }
    }

    private void handleConfirm() throws IOException, ClassNotFoundException {
        CurrencyName source = sourceCurrencyBox.getValue();
        CurrencyName target = targetCurrencyBox.getValue();
        double amount = Double.parseDouble(amountField.getText());
        // Add logic to handle confirmation of the swap request
        System.out.println("Confirmed Swap: " + amount + " " + source + " to " + (exchangeRate * amount) + " " + target);
    }

    public VBox getLayout() {
        return layout;
    }
}
