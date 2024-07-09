package moneyexchange;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

public class Wallet extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Wallet");

        // Define the x and y axes
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        //xAxis.setLabel("");
        yAxis.setLabel("Amount");

        // Creat the bar chart
        final BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Chart of Currencies you have");

        // Sett size of chart
        barChart.setPrefSize(400, 300);

        // Creating a series to hold the data points
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Base Currency: TOMAN");

        // Adding data points to the series
        XYChart.Data<String, Number> data1 = new XYChart.Data<>("USD", LoginSignupPage.database.updateUSD(LoginSignupPage.UserName, 0));
        XYChart.Data<String, Number> data2 = new XYChart.Data<>("EUR", LoginSignupPage.database.updateEUR(LoginSignupPage.UserName, 0));
        XYChart.Data<String, Number> data3 = new XYChart.Data<>("TOMAN", LoginSignupPage.database.updateTOMAN(LoginSignupPage.UserName, 0));
        XYChart.Data<String, Number> data4 = new XYChart.Data<>("YEN", LoginSignupPage.database.updateYEN(LoginSignupPage.UserName, 0));
        XYChart.Data<String, Number> data5 = new XYChart.Data<>("GBP", LoginSignupPage.database.updateGBP(LoginSignupPage.UserName, 0));



        // Setting styles for each bar
        data1.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: red;"));
        data2.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: blue;"));
        data3.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: green;"));
        data4.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: orange;"));
        data5.nodeProperty().addListener((ov, oldNode, newNode) -> newNode.setStyle("-fx-bar-fill: pink;"));


        // Add labels above each bar
        addLabelToData(data1);
        addLabelToData(data2);
        addLabelToData(data3);
        addLabelToData(data4);
        addLabelToData(data5);

        // Add data points to the series
        series.getData().add(data1);
        series.getData().add(data2);
        series.getData().add(data3);
        series.getData().add(data4);
        series.getData().add(data5);

        // Add the series to the bar chart
        barChart.getData().add(series);

        // Creat a VBox to hold the bar chart and other components
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().add(barChart);

        // Add a line under the chart
        Line line = new Line(0, 0, 800, 0);
        vbox.getChildren().add(line);

        // Creat HBoxes to hold the currency names and amounts
        HBox hbox1 = new HBox(10);
        hbox1.setAlignment(Pos.CENTER);
        Label usdLabel = new Label("USD: " + LoginSignupPage.database.updateUSD(LoginSignupPage.UserName, 0));
        usdLabel.setStyle("-fx-font-size: 25px;");
        hbox1.getChildren().add(usdLabel);

        HBox hbox2 = new HBox(10);
        hbox2.setAlignment(Pos.CENTER);
        Label eurLabel = new Label("EUR: " + LoginSignupPage.database.updateEUR(LoginSignupPage.UserName, 0));
        eurLabel.setStyle("-fx-font-size: 25px;");
        hbox2.getChildren().add(eurLabel);

        HBox hbox3 = new HBox(10);
        hbox3.setAlignment(Pos.CENTER);
        Label tomanLabel = new Label("TOMAN: " + LoginSignupPage.database.updateTOMAN(LoginSignupPage.UserName, 0));
        tomanLabel.setStyle("-fx-text-fill: #c71770; -fx-font-size: 25px;");
        hbox3.getChildren().add(tomanLabel);

        HBox hbox4 = new HBox(10);
        hbox4.setAlignment(Pos.CENTER);
        Label yenLabel = new Label("YEN: " + LoginSignupPage.database.updateYEN(LoginSignupPage.UserName, 0));
        yenLabel.setStyle("-fx-font-size: 25px;");
        hbox4.getChildren().add(yenLabel);

        HBox hbox5 = new HBox(10);
        hbox5.setAlignment(Pos.CENTER);
        Label gbpLabel = new Label("GBP: " + LoginSignupPage.database.updateGBP(LoginSignupPage.UserName, 0));
        gbpLabel.setStyle("-fx-font-size: 25px;");
        hbox5.getChildren().add(gbpLabel);

        // Creat a VBox to contain all HBoxes with a background
        VBox detailsBox = new VBox(10);
        detailsBox.setAlignment(Pos.CENTER);
        detailsBox.setPadding(new Insets(10));
        detailsBox.getChildren().addAll(hbox1, hbox2, hbox3, hbox4, hbox5);

        // Creat a background rectangle
        Rectangle backgroundRect = new Rectangle(500, 200);
        backgroundRect.setArcWidth(20);
        backgroundRect.setArcHeight(20);
        backgroundRect.setFill(Color.LIGHTBLUE);

        // Creat a StackPane to hold the rectangle and detailsBox
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(backgroundRect, detailsBox);
        stackPane.setPadding(new Insets(20));

        // Add the StackPane to the main VBox
        vbox.getChildren().add(stackPane);

        // Creat the scene with the VBox
        Scene scene = new Scene(vbox, 1000, 800);
        stage.setScene(scene);
        stage.show();
    }


    private void addLabelToData(XYChart.Data<String, Number> data) {
        Label label = new Label(data.getYValue().toString());
        label.setStyle("-fx-font-size: 12px; -fx-text-fill: #7a31dc;");
        label.setAlignment(Pos.CENTER);
        data.setNode(label);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
