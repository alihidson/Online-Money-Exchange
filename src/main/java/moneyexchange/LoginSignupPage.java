package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.regex.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class LoginSignupPage extends Application {

    private TextField usernameField, newUsernameField, emailField, phoneNumber;

    private TextField newPasswordField, newPasswordAgain;
    private PasswordField passwordField;
    private Database database;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login / Sign Up");

        database = new Database();

        // Login form
        Label loginLabel = new Label("Login");
        loginLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: rgb(76, 175, 80); -fx-text-fill: white; -fx-font-size: 16px;");
        loginButton.setOnAction(e -> login());


        VBox loginVBox = new VBox(10);
        loginVBox.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);
        loginVBox.setPadding(new Insets(10));
        loginVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,171,255), CornerRadii.EMPTY, Insets.EMPTY)));

        // Sign up form
        Label signupLabel = new Label("Sign Up");
        signupLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");
        newUsernameField.setMaxWidth(200);

        newPasswordField = new TextField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setMaxWidth(200);


        newPasswordAgain = new TextField();
        newPasswordAgain.setPromptText("New Password Again");
        newPasswordAgain.setMaxWidth(200);


        phoneNumber = new TextField();
        phoneNumber.setPromptText("Phone Number");
        phoneNumber.setMaxWidth(200);


        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);


        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: rgb(0, 140, 186); -fx-text-fill: white; -fx-font-size: 16px;");
        signupButton.setOnAction(e -> signup());
        VBox signupVBox = new VBox(10);
        signupVBox.getChildren().addAll(signupLabel, newUsernameField, newPasswordField, newPasswordAgain, phoneNumber,emailField, signupButton);
        signupVBox.setPadding(new Insets(10));
        signupVBox.setBackground(new Background(new BackgroundFill(Color.rgb(255,171,255), CornerRadii.EMPTY, Insets.EMPTY)));
        signupVBox.setVisible(false);

        // Toggle Button
        Button toggleButton = new Button("Don't have an account? Sign Up");
        toggleButton.setStyle("-fx-background-color: rgb(244, 67, 54); -fx-text-fill: white; -fx-font-size: 14px;");
        toggleButton.setOnAction(e -> {
            loginVBox.setVisible(!loginVBox.isVisible());
            signupVBox.setVisible(!signupVBox.isVisible());

            if (loginVBox.isVisible()) {
                toggleButton.setText("Don't have an account? Sign Up");
            }
            else {
                toggleButton.setText("Already have an account? Login");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().addAll(loginVBox, signupVBox, toggleButton);
        StackPane.setMargin(loginVBox, new Insets(50, 0, 0, 0));
        StackPane.setMargin(signupVBox, new Insets(50, 0, 0, 0));
        StackPane.setMargin(toggleButton, new Insets(300, 0, 0, 0));
        root.setBackground(new Background(new BackgroundFill(Color.rgb(141, 8, 79), CornerRadii.EMPTY, Insets.EMPTY)));
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);




        String buttonIdleStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px;";
        String buttonHoverStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 2px;";

        String loginButtonIdleColor = "rgb(69, 160, 73)";
        String loginButtonHoverColor = "rgb(69, 160, 73)";
        String signupButtonIdleColor = "rgb(0, 123, 156)";
        String signupButtonHoverColor = "rgb(0, 140, 186)";

        loginButton.setOnMouseEntered(e -> loginButton.setStyle(String.format(buttonHoverStyle, loginButtonHoverColor)));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(String.format(buttonIdleStyle, loginButtonIdleColor)));
        signupButton.setOnMouseEntered(e -> signupButton.setStyle(String.format(buttonHoverStyle, signupButtonHoverColor)));
        signupButton.setOnMouseExited(e -> signupButton.setStyle(String.format(buttonIdleStyle, signupButtonIdleColor)));

        primaryStage.show();
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (database.validateUser(username, password)) {
            System.out.println("Login Successful");
        }
        else {
            System.out.println("Invalid Username or Password");
        }
    }



    private void signup() {

        int sw = 1;

        if(newUsernameField.getText() == null || newPasswordField.getText() == null
                || emailField.getText() == null || newPasswordAgain.getText() == null
                || phoneNumber.getText() == null) {

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("No field should be empty, please try again");
            sw = 0;
            alert.showAndWait();

        }

        else if(!isValidEmail(emailField.getText())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("This Email is not valid, please try again");
            sw = 0;
            alert.showAndWait();

        }

        else if(!isPasswordValid(newPasswordField.getText())) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Sorry");
            alert.setContentText("This Password is not valid, please try again");
            sw = 0;
            alert.showAndWait();
        }

        else {
            for(int i=0; i< newPasswordField.getText().length() && i< newPasswordField.getText().length(); i++) {
                if(newPasswordField.getText().charAt(i) != newPasswordAgain.getText().charAt(i)) {

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setHeaderText("Sorry");
                    alert.setContentText("This new Password is not equal by again Password, please try again");
                    sw = 0;
                    alert.showAndWait();

                }
            }
        }



        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        String newEmail = emailField.getText();
        String newPhoneNumber = phoneNumber.getText();



        if(sw == 1) {
            database.addUser(newUsername, newPassword);
        }

        if (sw == 1) {
            System.out.println("Sign Up Successful");
        }
        else {
            System.out.println("Sign Up Failed");
        }
    }



    public static boolean isPasswordValid(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d).+$";
        Pattern patternPassword = Pattern.compile(regex);
        Matcher matcherPassword = patternPassword.matcher(password);
        return matcherPassword.find();
    }

    public static boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern patternEmail = Pattern.compile(regex);
        Matcher matcherEmail = patternEmail.matcher(email);
        return matcherEmail.matches();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
