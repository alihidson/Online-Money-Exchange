package moneyexchange.moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginSignupPage extends Application {

    private TextField usernameField, newUsernameField;
    private PasswordField passwordField, newPasswordField;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login / Sign Up");

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


        VBox loginVBox = new VBox(10);
        loginVBox.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);
        loginVBox.setPadding(new Insets(10));
        loginVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        // Sign up form
        Label signupLabel = new Label("Sign Up");
        signupLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");
        newUsernameField.setMaxWidth(200);

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setMaxWidth(200);

        Button signupButton = new Button("Sign Up");
        signupButton.setStyle("-fx-background-color: rgb(0, 140, 186); -fx-text-fill: white; -fx-font-size: 16px;");
        VBox signupVBox = new VBox(10);
        signupVBox.getChildren().addAll(signupLabel, newUsernameField, newPasswordField, signupButton);
        signupVBox.setPadding(new Insets(10));
        signupVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        signupVBox.setVisible(false);


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

        VBox root = new VBox(10);
        root.getChildren().addAll(loginVBox, signupVBox, toggleButton);
        root.setPadding(new Insets(20));
        root.setBackground(new Background(new BackgroundFill(Color.rgb(141, 8, 79), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(new Scene(root, 500, 400));

        // Hover effects for buttons
        String buttonIdleStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px;";
        String buttonHoverStyle = "-fx-background-color: %s; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-color: white; -fx-border-width: 2px;";
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(String.format(buttonHoverStyle, "#45a049")));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(String.format(buttonIdleStyle, "#4CAF50")));
        signupButton.setOnMouseEntered(e -> signupButton.setStyle(String.format(buttonHoverStyle, "#007B9C")));
        signupButton.setOnMouseExited(e -> signupButton.setStyle(String.format(buttonIdleStyle, "#008CBA")));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


