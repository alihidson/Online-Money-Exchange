package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Transfer extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Transfer");


        Label usernameLabel = new Label("Wallet id:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label currencyLabel = new Label("Select Currency for buy:");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("USD", "EUR", "TOMAN", "YEN", "GBP");
        currencyComboBox.setPromptText("Select a currency");

        Label amountLabel = new Label("Amount:");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter the amount for buy");

        Button acceptButton = new Button("Accept");


        acceptButton.setOnAction(e -> {
            String username = usernameField.getText();
            String currency = currencyComboBox.getValue();
            String amount = amountField.getText();

            if(currency == "USD") {
                LoginSignupPage.database.updateUSD(username, Double.parseDouble(amount));
            }
            else if(currency == "EUR") {
                LoginSignupPage.database.updateEUR(username, Double.parseDouble(amount));
            }
            else if(currency == "TOMAN") {
                LoginSignupPage.database.updateTOMAN(username, Double.parseDouble(amount));
            }
            else if(currency == "YEN") {
                LoginSignupPage.database.updateYEN(username, Double.parseDouble(amount));
            }
            else if(currency == "GBP") {
                LoginSignupPage.database.updateGBP(username, Double.parseDouble(amount));
            }

        });


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(usernameLabel, usernameField, currencyLabel, currencyComboBox, amountLabel, amountField, acceptButton);


        Scene scene = new Scene(vbox, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

