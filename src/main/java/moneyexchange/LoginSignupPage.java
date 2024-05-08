package moneyexchange;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginSignupPage extends Application {

    private TextField usernameField, newUsernameField;
    private PasswordField passwordField, newPasswordField;
    private Database dataBase;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login / Sign Up");

        dataBase = new Database();


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
        loginVBox.setBackground(new Background(new BackgroundFill(Color.rgb(141,8,79), CornerRadii.EMPTY, Insets.EMPTY)));

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
        signupButton.setOnAction(e -> signup());
        VBox signupVBox = new VBox(10);
        signupVBox.getChildren().addAll(signupLabel, newUsernameField, newPasswordField, signupButton);
        signupVBox.setPadding(new Insets(10));
        signupVBox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
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

        VBox centerBox = new VBox();
        centerBox.getChildren().addAll(loginVBox, signupVBox, toggleButton);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(10);

        VBox root = new VBox();
        root.getChildren().add(centerBox);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.rgb(0, 8, 79), CornerRadii.EMPTY, Insets.EMPTY)));
        primaryStage.setScene(new Scene(root, 500, 400, Color.BLUE));

        primaryStage.show();
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (dataBase.validateUser(username, password)) {
            System.out.println("Login Successful");
        }
        else {
            System.out.println("Invalid Username or Password");
        }
    }

    private void signup() {
        String newUsername = newUsernameField.getText();
        String newPassword = newPasswordField.getText();
        if (dataBase.addUser(newUsername, newPassword)) {
            System.out.println("Sign Up Successful");
        }
        else {
            System.out.println("Sign Up Failed");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
