package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Digital Currencies");

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

        MenuItem profileItem = new MenuItem("profile");
        profileItem.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: black;");

//        profileItem.setOnAction(e -> openProfilePage());
        profileMenu.getItems().add(profileItem);

        menuBar.getMenus().addAll(exitMenu, profileMenu);

        // Information of Digital Currencies
        TableView<CurrencyInfo> tableView = new TableView<>();
        TableColumn<CurrencyInfo, String> currencyColumn = new TableColumn<>("Currency");
        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        TableColumn<CurrencyInfo, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<CurrencyInfo, Double> changeColumn = new TableColumn<>("Change");
        changeColumn.setCellValueFactory(new PropertyValueFactory<>("change"));

        TableColumn<CurrencyInfo, Double> highestColumn = new TableColumn<>("Highest");
        highestColumn.setCellValueFactory(new PropertyValueFactory<>("highest"));

        TableColumn<CurrencyInfo, Double> lowestColumn = new TableColumn<>("Lowest");
        lowestColumn.setCellValueFactory(new PropertyValueFactory<>("lowest"));


        priceColumn.setCellFactory(column -> {
            return new TableCell<CurrencyInfo, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    }
                    else {
                        setText(String.valueOf(item));
                        if (item > 100) {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                        else if (item < 50) {
                            setStyle("-fx-background-color: lightcoral;");
                        }
                        else {
                            setStyle("");
                        }
                    }
                }
            };
        });


        changeColumn.setCellFactory(column -> {
            return new TableCell<CurrencyInfo, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    }
                    else {
                        setText(String.valueOf(item));
                        if (item > 0) {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                        else if (item < 0) {
                            setStyle("-fx-background-color: lightcoral;");
                        }
                        else {
                            setStyle("");
                        }
                    }
                }
            };
        });


        highestColumn.setCellFactory(column -> {
            return new TableCell<CurrencyInfo, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    }
                    else {
                        setText(String.valueOf(item));
                        if (item > 0) {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                        else if (item < 0) {
                            setStyle("-fx-background-color: lightcoral;");
                        }
                        else {
                            setStyle("");
                        }
                    }
                }
            };
        });


        lowestColumn.setCellFactory(column -> {
            return new TableCell<CurrencyInfo, Double>() {
                @Override
                protected void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    }
                    else {
                        setText(String.valueOf(item));
                        if (item > 100) {
                            setStyle("-fx-background-color: lightgreen;");
                        }
                        else if (item < 50) {
                            setStyle("-fx-background-color: lightcoral;");
                        }
                        else {
                            setStyle("");
                        }
                    }
                }
            };
        });

        tableView.getColumns().addAll(currencyColumn, priceColumn, changeColumn, highestColumn, lowestColumn);

        // Adding data to the table
        tableView.getItems().addAll(
                new CurrencyInfo("Bitcoin", 50000.00, 2.5, 51000.00, 48000.00),
                new CurrencyInfo("Dogecoin", 0.30, 1.8, 0.35, 0.25),
                new CurrencyInfo("Dash", 150.00, -0.5, 155.00, 145.00)
        );

        // Adjust the height and cell size of the table to fit the rows
        tableView.setFixedCellSize(25); // Set a fixed cell size
        tableView.prefHeightProperty().bind(tableView.fixedCellSizeProperty().multiply(tableView.getItems().size() + 1.25)); // Adjust height

        // Adjust the width of the table to fit the columns
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        currencyColumn.setMinWidth(50);
        priceColumn.setMinWidth(50);
        changeColumn.setMinWidth(50);
        highestColumn.setMinWidth(50);
        lowestColumn.setMinWidth(50);

        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                CurrencyInfo selectedCurrency = tableView.getSelectionModel().getSelectedItem();
                if (selectedCurrency != null && selectedCurrency.getCurrency().equals("Bitcoin")) {
                    openBitcoin(selectedCurrency.getCurrency());
                }
            }
        });

        VBox tableContainer = new VBox(tableView);
        tableContainer.setPadding(new Insets(20));
        tableContainer.setStyle("-fx-background-color: rgb(255, 123, 70);");

        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(tableContainer);

        Scene scene = new Scene(root, 1280, 740);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void openBitcoin(String currencyName) {
        Stage currencyStage = new Stage();
        Bitcoin bitcoin = new Bitcoin(currencyName);
        bitcoin.start(currencyStage);
    }

//    public void openProfilePage() {
//        Stage profileStage = new Stage();
//        ProfilePage profilePage = new ProfilePage();
//        profilePage.start(profileStage);
//    }

    public static void main(String[] args) {
        launch(args);
    }
}