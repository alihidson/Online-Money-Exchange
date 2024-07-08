package moneyexchange;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Wallet extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Wallet");

        // Defining the x and y axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Currencies");
        yAxis.setLabel("Amount");

        // Creating the bar chart
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Chart of Currencies you have");

        // Setting the preferred size for the bar chart
        barChart.setPrefSize(400, 300);

        // Creating a series to hold the data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Currencies");

        // Adding data points to the series
        XYChart.Data<String, Number> data1 = new XYChart.Data<>("USD", 50);
        XYChart.Data<String, Number> data2 = new XYChart.Data<>("EUR", 60);
        XYChart.Data<String, Number> data3 = new XYChart.Data<>("TOMAN", 20);
        XYChart.Data<String, Number> data4 = new XYChart.Data<>("YEN", 33);
        XYChart.Data<String, Number> data5 = new XYChart.Data<>("GBP", 10);

        // Setting styles for each bar
        data1.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: red;"));
        data2.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: blue;"));
        data3.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: green;"));
        data4.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: orange;"));
        data5.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: pink;"));

        // Adding data points to the series
        series.getData().add(data1);
        series.getData().add(data2);
        series.getData().add(data3);
        series.getData().add(data4);
        series.getData().add(data5);

        // Adding the series to the bar chart
        barChart.getData().add(series);

        // Creating a VBox to hold the bar chart and other components
        VBox vbox = new VBox();
        vbox.getChildren().add(barChart);

        // Creating the scene with the VBox
        Scene scene = new Scene(vbox, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
