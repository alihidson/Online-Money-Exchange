package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ProfilePage extends Application {

    private String username;
    private String firstName;
    private String lastName;
    private String Age;
    private String password;
    private String phoneNumber;
    private String email;
    String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/person.jpg";



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

        TextField AgeField = new TextField(Age);

        TextField passwordField = new TextField(password);

        TextField phoneNumberField = new TextField(phoneNumber);

        TextField emailField = new TextField(email);



        // Profile image
        ImageView profileImageView = new ImageView(new Image(imagePath));
        profileImageView.setFitWidth(100);
        profileImageView.setFitHeight(100);
        profileImageView.setPreserveRatio(true);


        Circle clip = new Circle(50, 35, 30); // x, y, radius
        profileImageView.setClip(clip);

        // Buttons
        Button editButton = new Button("Edit Profile");
        Button walletButton = new Button("Wallet");
        Button historyButton = new Button("History");

        // Layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setBackground(new Background(new BackgroundFill(Color.rgb(45, 201, 198), CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.setBorder(new javafx.scene.layout.Border(new javafx.scene.layout.BorderStroke(
                Color.rgb(103, 23, 201),
                javafx.scene.layout.BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        vbox.getChildren().addAll(
                profileImageView,
                usernameLabel,
                new HBox(10, new Label("First Name:"), firstNameField),
                new HBox(10, new Label("Last Name:"), lastNameField),
                new HBox(10, new Label("Age:"), AgeField),
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
            Age = AgeField.getText();
            password = passwordField.getText();
            phoneNumber = phoneNumberField.getText();
            email = emailField.getText();


            LoginSignupPage.database.updateFirstName(username, firstName);
            LoginSignupPage.database.updateLastName(username, lastName);
            LoginSignupPage.database.updateAge(username, Age);
            LoginSignupPage.database.updatePassword(username, password);
            LoginSignupPage.database.updatePhoneNumber(username, phoneNumber);
            LoginSignupPage.database.updateEmail(username, email);



            System.out.println("Profile updated!");
        });

        Scene scene = new Scene(vbox, 400, 500);

        primaryStage.setTitle("Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
