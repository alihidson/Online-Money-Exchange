package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HomePage extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Welcome");

        Label welcomeLabel = new Label("Hello baby");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        StackPane root = new StackPane();
        root.getChildren().add(welcomeLabel);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
