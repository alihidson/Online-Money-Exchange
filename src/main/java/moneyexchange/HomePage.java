package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
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

        tableView.getColumns().addAll(currencyColumn, priceColumn, changeColumn, highestColumn, lowestColumn);

        tableView.getItems().addAll(
                new CurrencyInfo("Bitcoin", 50000.00, 2.5, 51000.00, 48000.00),
                new CurrencyInfo("Dogecoin", 0.30, 1.8, 0.35, 0.25),
                new CurrencyInfo("Dash", 150.00, -0.5, 155.00, 145.00)
        );


        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                CurrencyInfo selectedCurrency = tableView.getSelectionModel().getSelectedItem();
                if (selectedCurrency != null && selectedCurrency.getCurrency().equals("Bitcoin")) {
                    openBitcoin(selectedCurrency.getCurrency());
                }
            }
        });

        VBox root = new VBox(tableView);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: rgb(255, 123, 70);");

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void openBitcoin(String currencyName) {
        Stage currencyStage = new Stage();
        Bitcoin bitcoin = new Bitcoin(currencyName);
        bitcoin.start(currencyStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
