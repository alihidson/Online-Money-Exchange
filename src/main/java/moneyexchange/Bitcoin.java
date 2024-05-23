package moneyexchange;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Bitcoin extends Application {
    private String currencyName;

    public Bitcoin(String currencyName) {
        this.currencyName = currencyName;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle(currencyName);

        Label label = new Label("Welcome to " + currencyName + " page");

        StackPane root = new StackPane();
        root.getChildren().add(label);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
