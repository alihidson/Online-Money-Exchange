package moneyexchange;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProfilePage extends Application {

    private String username;
    private String phoneNumber;
    private String email;

    public ProfilePage(String username, String phoneNumber, String email) {
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    @Override
    public void start(Stage primaryStage) {
        Label usernameLabel = new Label("Username: " + username);
        Label phoneNumberLabel = new Label("Phone Number: " + phoneNumber);
        Label emailLabel = new Label("Email: " + email);

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(usernameLabel, phoneNumberLabel, emailLabel);

        Scene scene = new Scene(vbox, 300, 200);


        primaryStage.setTitle("User Profile");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}


