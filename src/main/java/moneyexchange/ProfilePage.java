package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class ProfilePage extends Application {

    private String username;
    private String firstName;
    private String lastName;
    private String Age;
    private String password;
    private String phoneNumber;
    private String email;
    String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/background.jpg";
    private Image profileImage = new Image(imagePath);



        public ProfilePage(String username,String firstName, String lastName, String Age,
                           String password, String phoneNumber, String email) {

        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Age = Age;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


    @Override
    public void start(Stage primaryStage) {
        Label usernameLabel = new Label("Username: " + username);


        TextField firstNameField = new TextField(firstName);

        TextField lastNameField = new TextField(lastName);

        TextField passwordField = new TextField(password);

        TextField phoneNumberField = new TextField(phoneNumber);

        TextField emailField = new TextField(email);



        // Profile image
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(true);

        // Buttons
        Button editButton = new Button("Edit Profile");
        Button walletButton = new Button("Wallet");
        Button historyButton = new Button("History");

        // Layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(
                Color.GRAY,
                javafx.scene.layout.BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                javafx.scene.layout.BorderWidths.DEFAULT)));

        vbox.getChildren().addAll(
                profileImageView,
                usernameLabel,
                new HBox(10, new Label("First Name:"), firstNameField),
                new HBox(10, new Label("Last Name:"), lastNameField),
                new HBox(10, new Label("Password:"), passwordField),
                new HBox(10, new Label("Phone Number:"), phoneNumberField),
                new HBox(10, new Label("Email:"), emailField),
                editButton,
                walletButton,
                historyButton
        );


        editButton.setOnAction(event -> {

            firstName = firstNameField.getText();
            lastName = lastNameField.getText();
            password = passwordField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();

            System.out.println("Profile updated!");
        });

        Scene scene = new Scene(vbox, 400, 500);

        primaryStage.setTitle("User Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
