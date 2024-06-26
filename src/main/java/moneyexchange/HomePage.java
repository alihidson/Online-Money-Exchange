package moneyexchange;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends Application {

    private TableView<CurrencyInfo> table = new TableView<>();
    private List<List<CurrencyInfo>> prices = new ArrayList<>();
    private int currentLine = 0;

    @Override
    public void start(Stage primaryStage) {

        String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/icon.png";
        Image icon = new Image(imagePath);
        primaryStage.getIcons().add(icon);

        primaryStage.setResizable(false);

        // Create MenuBar
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: rgb(213,28,124);");

        // Create label for the menu
        Label finishLabel = new Label("Finish");
        finishLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Menu exitMenu = new Menu();
        exitMenu.setGraphic(finishLabel);

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black;");
        exitItem.setOnAction(e -> {
            primaryStage.close();
            Stage LoginPage = new Stage();
            LoginSignupPage loginSignupPage = new LoginSignupPage();
            loginSignupPage.start(LoginPage);
        });

        exitMenu.getItems().add(exitItem);

        // Add Profile menu
        Label profileLabel = new Label("Profile");
        profileLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");

        Menu profileMenu = new Menu();
        profileMenu.setGraphic(profileLabel);

        MenuItem profileItem = new MenuItem("Profile");
        profileItem.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black;");
        profileItem.setOnAction(e -> openProfilePage());
        profileMenu.getItems().add(profileItem);

        menuBar.getMenus().addAll(exitMenu, profileMenu);

        // Setup columns
        TableColumn<CurrencyInfo, String> nameColumn = new TableColumn<>("Currency");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<CurrencyInfo, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        table.getColumns().add(nameColumn);
        table.getColumns().add(priceColumn);

        // Read initial data
        readCSV();

        // create the scene
        VBox vbox = new VBox(menuBar, table); // Add the MenuBar to the VBox
        Scene scene = new Scene(vbox, 400, 300); // Adjust the size as needed
        primaryStage.setScene(scene);
        primaryStage.setTitle("Currency");
        primaryStage.show();

        // Show initial data
        updateTable();

        // updater to run every minute
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> updateTable()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void readCSV() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/ali/Main/Documents/Source/Money-Exchange/Currency-Data/data.csv"))) {
            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 7) { // Adjusted length check to >= 7
                    List<CurrencyInfo> linePrices = new ArrayList<>();
                    linePrices.add(new CurrencyInfo("USD", data[2]));
                    linePrices.add(new CurrencyInfo("EUR", data[3]));
                    linePrices.add(new CurrencyInfo("TOMAN", data[4]));
                    linePrices.add(new CurrencyInfo("YEN", data[5]));
                    linePrices.add(new CurrencyInfo("GBP", data[6]));
                    prices.add(linePrices);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateTable() {
        if (currentLine < prices.size()) {
            List<CurrencyInfo> linePrices = prices.get(currentLine);
            table.getItems().clear();
            table.getItems().addAll(linePrices);
            currentLine++;
        }
    }

    public void openProfilePage() {
        Stage profileStage = new Stage();

        ProfilePage profilePage = new ProfilePage(LoginSignupPage.UserName, LoginSignupPage.firstNameProf,
                LoginSignupPage.lastNameProf, LoginSignupPage.AgeProf, LoginSignupPage.PassWord,
                LoginSignupPage.phoneNumberProf, LoginSignupPage.emailProf);

        profilePage.start(profileStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
