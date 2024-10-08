package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends Application {

    private TableView<CurrencyInfo> tableView = new TableView<>();
    private List<List<CurrencyInfo>> prices = new ArrayList<>();
    private int currentLine = 0;

    @Override
    public void start(Stage primaryStage) {

        String imagePath = "file:/Users/ali/Main/Documents/Source/Money-Exchange/src/image/icon.png";
        Image icon = new Image(imagePath);
        primaryStage.getIcons().add(icon);

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
        exitItem.setOnAction(e -> ClosePage(primaryStage));
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

        // Add Transfer menu
        Label TransferLabel = new Label("Transfer");
        TransferLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: white;");
        Menu TransferMenu = new Menu();
        TransferMenu.setGraphic(TransferLabel);
        MenuItem TransferItem = new MenuItem("Transfer");
        TransferItem.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black;");
        TransferItem.setOnAction(e -> openTransferPage());
        TransferMenu.getItems().add(TransferItem);

        menuBar.getMenus().addAll(exitMenu, profileMenu, TransferMenu);

        // Setup columns
        TableColumn<CurrencyInfo, String> nameColumn = new TableColumn<>("Currency");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
        timeColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, Double> highestColumn = new TableColumn<>("Highest");
        highestColumn.setCellValueFactory(new PropertyValueFactory<>("maxValue"));
        highestColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, Double> lowestColumn = new TableColumn<>("Lowest");
        lowestColumn.setCellValueFactory(new PropertyValueFactory<>("minValue"));
        lowestColumn.setMinWidth(100);

        TableColumn<CurrencyInfo, String> changeColumn = new TableColumn<>("Change");
        changeColumn.setCellValueFactory(new PropertyValueFactory<>("change"));
        changeColumn.setMinWidth(100);

        tableView.getColumns().addAll(nameColumn, dateColumn, timeColumn, priceColumn, highestColumn, lowestColumn, changeColumn);

        // Set row height
        tableView.setRowFactory(tv -> {
            TableRow<CurrencyInfo> row = new TableRow<>();
            row.setPrefHeight(40); // Set preferred height for each row
            return row;
        });

        // Read initial data
        CSVReader csvReader = new CSVReader();
        prices = csvReader.readCSV("/Users/ali/Main/Documents/Source/Money-Exchange/Currency-Data/data.csv");

        VBox tableContainer = new VBox(tableView);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: rgb(255, 123, 70);");

        // Set border around the table container (all page)
        tableContainer.setBorder(new Border(
                new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        // Set border around the table view
        tableView.setBorder(new Border(
                new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(5))));

        // Set size for the tableView
        tableView.setMaxSize(720, 250);

        // Create BorderPane with MenuBar at the top and tablePane in the center
        BorderPane root = new BorderPane();
        root.setTop(menuBar);

        // Create Labels for display username and date and time
        Label greetingLabel = new Label("Username: " + LoginSignupPage.UserName);
        greetingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #4f4f00;");

        DateTimeDisplay dateTimeDisplay = new DateTimeDisplay();
        Label dateTimeLabel = dateTimeDisplay.getDateTimeLabel();

        // VBox to hold the labels and the table
        VBox contentBox = new VBox(10, greetingLabel, dateTimeLabel, tableContainer);
        contentBox.setPadding(new Insets(20));
        contentBox.setStyle("-fx-background-color: rgb(255, 123, 70);");

        root.setCenter(contentBox);


        // Create scene with BorderPane
        Scene scene = new Scene(root, 815, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Currency");
        primaryStage.show();

        // Show initial data
        showInitialData();

        // Updater to run every minute
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> updateTable()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showInitialData() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter formatterWithSeconds = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");

        // Find the closest time to display
        int closestIndex = 0;
        long closestDifference = Long.MAX_VALUE;

        for (int i = 0; i < prices.size(); i++) {
            List<CurrencyInfo> linePrices = prices.get(i);
            String timeString = linePrices.get(0).getTime();
            LocalTime priceTime = null;


            try {
                priceTime = LocalTime.parse(timeString, formatter);
            }
            catch (DateTimeParseException e) {
                try {
                    priceTime = LocalTime.parse(timeString, formatterWithSeconds);
                }
                catch (DateTimeParseException ex) {
                    continue;
                }
            }

            long difference = Math.abs(currentTime.toSecondOfDay() - priceTime.toSecondOfDay());
            if (difference < closestDifference) {
                closestDifference = difference;
                closestIndex = i;
            }
        }

        currentLine = closestIndex;
        updateTable();
    }


    private void updateTable() {
        if (currentLine < prices.size()) {
            List<CurrencyInfo> linePrices = prices.get(currentLine);
            Map<String, CurrencyInfo> currencyMap = new HashMap<>();

            for (CurrencyInfo currencyInfo : tableView.getItems()) {
                currencyMap.put(currencyInfo.getName(), currencyInfo);
            }

            for (CurrencyInfo newCurrencyInfo : linePrices) {
                CurrencyInfo existingCurrencyInfo = currencyMap.get(newCurrencyInfo.getName());
                if (existingCurrencyInfo != null) {
                    existingCurrencyInfo.setPrice(newCurrencyInfo.getPrice());
                    existingCurrencyInfo.setDate(newCurrencyInfo.getDate());
                    existingCurrencyInfo.setTime(newCurrencyInfo.getTime());
                }
                else {
                    tableView.getItems().add(newCurrencyInfo);
                }
            }

            tableView.refresh();
            currentLine++;
        }
    }


    public void ClosePage(Stage primaryStage) {
        primaryStage.close();
        Stage LoginPage = new Stage();
        LoginSignupPage loginSignupPage = new LoginSignupPage();
        loginSignupPage.start(LoginPage);
    }

    public void openProfilePage() {
        Stage profileStage = new Stage();

        ProfilePage profilePage = new ProfilePage(LoginSignupPage.UserName, LoginSignupPage.firstNameProf,
                LoginSignupPage.lastNameProf, LoginSignupPage.AgeProf, LoginSignupPage.PassWord,
                LoginSignupPage.phoneNumberProf, LoginSignupPage.emailProf);

        profilePage.start(profileStage);
    }

    public void openTransferPage() {
        Stage TransferStage = new Stage();
        Transfer transfer = new Transfer();
        transfer.start(TransferStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
