package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Transfer extends Application {

    String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/person.jpg";


    @Override
    public void start(Stage stage) {
        stage.setTitle("Transfer");

        // Profile image
        ImageView profileImageView = new ImageView(new Image(imagePath));
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(true);

        Circle clip = new Circle(50, 35, 35); // x, y, radius
        profileImageView.setClip(clip);


        Label usernameLabel = new Label("Wallet id:");
        usernameLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label currencyLabel = new Label("Select Currency for buy:");
        currencyLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        ComboBox<String> currencyComboBox = new ComboBox<>();
        currencyComboBox.getItems().addAll("USD", "EUR", "TOMAN", "YEN", "GBP");
        currencyComboBox.setPromptText("Select a currency");

        Label amountLabel = new Label("Amount:");
        amountLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        TextField amountField = new TextField();
        amountField.setPromptText("Enter the amount for buy");

        Button acceptButton = new Button("Accept");


        acceptButton.setOnAction(e -> {
            String username = usernameField.getText();
            String currency = currencyComboBox.getValue();
            String amount = amountField.getText();

            if(currency == "USD") {
                LoginSignupPage.database.updateUSD(username, "admin", Double.parseDouble(amount));
            }
            else if(currency == "EUR") {
                LoginSignupPage.database.updateEUR(username,"admin", Double.parseDouble(amount));
            }
            else if(currency == "TOMAN") {
                LoginSignupPage.database.updateTOMAN(username,"admin", Double.parseDouble(amount));
            }
            else if(currency == "YEN") {
                LoginSignupPage.database.updateYEN(username,"admin", Double.parseDouble(amount));
            }
            else if(currency == "GBP") {
                LoginSignupPage.database.updateGBP(username,"admin", Double.parseDouble(amount));
            }

        });


        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(profileImageView, usernameLabel, usernameField, currencyLabel, currencyComboBox, amountLabel, amountField, acceptButton);

        vbox.setStyle("-fx-background-color: #667e14;");

        Scene scene = new Scene(vbox, 500, 450);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}