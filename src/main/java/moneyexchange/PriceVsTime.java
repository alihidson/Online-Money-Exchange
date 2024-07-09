package moneyexchange;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PriceVsTime {
    private CSVToArray dataset = new CSVToArray("/data/currency_prices.csv");
    private NumberAxis xAxis = new NumberAxis();
    private NumberAxis yAxis = new NumberAxis();
    private LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
    private XYChart.Series<Number, Number> series = new XYChart.Series<>();
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private VBox rootLayout;

    private Timeline timeline;
    private TimePeriod timePeriod = TimePeriod.ONE_MINUTE;
    private ScheduledExecutorService executorService;


    private int currentIndex = 1;

    public static int compareTime(String s1, String s2) {
        try {
            // Extract hours and minutes
            int h1 = Integer.parseInt(s1.substring(0, 2));
            int h2 = Integer.parseInt(s2.substring(0, 2));
            int m1 = Integer.parseInt(s1.substring(3, 5));
            int m2 = Integer.parseInt(s2.substring(3, 5));

            // Compare hours
            if (h1 > h2) return 1;
            else if (h1 < h2) return -1;
            else {
                // Compare minutes if hours are equal
                if (m1 > m2) return 1;
                else if (m1 < m2) return -1;
                else return 0;
            }
        } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
            System.err.println("Invalid time format: " + e.getMessage());
            return 0; // Or handle the error as appropriate
        }
    }


    public PriceVsTime(CurrencyName currencyName) {
        setLabel(currencyName);
        updateData(currencyName, TimePeriod.ONE_MINUTE);
        lineChart.getData().add(series);
        setChartSize(600, 400);
        styleChart();
        initializeLayout(currencyName);

        startAutoUpdate(currencyName, timePeriod);
    }

    public void startAutoUpdate(CurrencyName currencyName, TimePeriod timePeriod) {
        if (timeline != null) {
            timeline.stop();
        }

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Duration duration = getDurationForTimePeriod(timePeriod);
                timeline = new Timeline(new KeyFrame(duration, event -> {
                    updateData(currencyName, timePeriod);
                    System.out.println(currentIndex);
                }));
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                return null;
            }
        };

        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private Duration getDurationForTimePeriod(TimePeriod timePeriod) {
        switch (timePeriod) {
            case ONE_MINUTE:
                return Duration.minutes(1);
            case THIRTY_MINUTES:
                return Duration.minutes(30);
            case ONE_HOUR:
                return Duration.hours(1);
            case THREE_HOURS:
                return Duration.hours(3);
            case SIX_HOURS:
                return Duration.hours(6);
            case TWELVE_HOURS:
                return Duration.hours(12);
            default:
                throw new IllegalArgumentException("Unsupported time period: " + timePeriod);
        }
    }


    public void stopAutoUpdate() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
    }

    private void setChartSize(double width, double height) {
        lineChart.setPrefWidth(width);
        lineChart.setPrefHeight(height);
    }

    private void setLabel(CurrencyName currencyName) {
        xAxis.setLabel("Time");
        yAxis.setLabel("Price");
        lineChart.setTitle("Price Trend");

        switch (currencyName) {
            case USD:
                series.setName("USD price");
                break;
            case EUR:
                series.setName("EUR price");
                break;
            case TOMAN:
                series.setName("TOMAN price");
                break;
            case YEN:
                series.setName("YEN price");
                break;
            case GBP:
                series.setName("GBP price");
                break;
        }
    }

    private void styleChart() {
        lineChart.setStyle(
                "-fx-background-color: #f0f8ff; " +
                        "-fx-border-color: #4682b4; " +
                        "-fx-border-width: 2;"
        );

        lineChart.lookup(".chart-title").setStyle(
                "-fx-font-size: 16px; " +
                        "-fx-text-fill: #4682b4;"
        );

        xAxis.lookup(".axis-label").setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: #4682b4;"
        );
        yAxis.lookup(".axis-label").setStyle(
                "-fx-font-size: 14px; " +
                        "-fx-text-fill: #4682b4;"
        );

        series.getNode().lookup(".chart-series-line").setStyle(
                "-fx-stroke: #4682b4; " +
                        "-fx-stroke-width: 2px; " +
                        "-fx-stroke-line-cap: round;"
        );
    }

    private void initializeLayout(CurrencyName currencyName) {
        Button sixHoursButton = createStyledButton("6 Hours");
        Button twelveHoursButton = createStyledButton("12 Hours");
        Button threeHoursButton = createStyledButton("3 Hours");
        Button thirtyMinutesButton = createStyledButton("30 minutes");
        Button oneMinuteButton = createStyledButton("1 minute");
        Button oneHourButton = createStyledButton("1 hour");

        sixHoursButton.setOnAction(e -> {
            timePeriod = TimePeriod.SIX_HOURS;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        twelveHoursButton.setOnAction(e -> {
            timePeriod = TimePeriod.TWELVE_HOURS;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        threeHoursButton.setOnAction(e -> {
            timePeriod = TimePeriod.THREE_HOURS;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        thirtyMinutesButton.setOnAction(e -> {
            timePeriod = TimePeriod.THIRTY_MINUTES;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        oneMinuteButton.setOnAction(e -> {
            timePeriod = TimePeriod.ONE_MINUTE;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        oneHourButton.setOnAction(e -> {
            timePeriod = TimePeriod.ONE_HOUR;
            currentIndex = 1;
            updateData(currencyName, timePeriod);
        });

        HBox buttons = new HBox(10);
        buttons.setPadding(new Insets(10));
        buttons.getChildren().addAll(oneMinuteButton, thirtyMinutesButton, oneHourButton, threeHoursButton, sixHoursButton, twelveHoursButton);
        buttons.setAlignment(Pos.CENTER);

        rootLayout = new VBox(10, lineChart, buttons);
        rootLayout.setPadding(new Insets(20));
        rootLayout.setStyle(
                "-fx-background-color: #ffffff; " +
                        "-fx-border-color: #4682b4; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 10;"
        );
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: linear-gradient(to left, #1565C0, #80DEEA);" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-padding: 10 20 10 20;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,15,0,0.4), 10, 0, 0, 5);"
        );
        return button;
    }

    public void addData(CurrencyName currencyName, TimePeriod timePeriod, LocalTime now) {
        String timeString = now.format(timeFormatter);

        double price;
        for (int i = 1; i < dataset.getDataArray(timePeriod).length; i++) {
            String[] row = dataset.getDataArray(timePeriod)[i];
            if (row.length <= 1) {
                continue;
            }

            if (compareTime(timeString, row[1]) <= 0) {
                // System.out.println("//");
                // System.out.println("out");
                // System.out.println(timeString);
                // System.out.println(row[1]);
                // System.out.println("//");
                break;
            }

            // else{
            //     System.out.println("//");
            //     System.out.println("in");
            //     System.out.println(timeString);
            //     System.out.println(row[1]);
            //     System.out.println("//");
            // }

            now = LocalTime.parse(row[1], timeFormatter);
            switch (currencyName) {
                case USD:
                    price = Double.parseDouble(row[2]);
                    break;
                case EUR:
                    price = Double.parseDouble(row[3]);
                    break;
                case TOMAN:
                    price = Double.parseDouble(row[4]);
                    break;
                case YEN:
                    price = Double.parseDouble(row[5]);
                    break;
                case GBP:
                    price = Double.parseDouble(row[6]);
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported currency: " + currencyName);
            }
            //System.out.println("Price: " + price);
            series.getData().add(new XYChart.Data<>(now.toSecondOfDay(), price));
        }
    }

    public void updateData(CurrencyName currencyName, TimePeriod timePeriod) {
        Platform.runLater(() -> {
            LocalTime now = LocalTime.now();
            series.getData().clear();
            addData(currencyName, timePeriod, now);
        });
    }

    /*  public void startAutoUpdate(CurrencyName currencyName, TimePeriod timePeriod) {
    //     if (timeline != null) {
    //         timeline.stop();
    //     }

    //     Duration duration = Duration.minutes(1);
    //     switch (timePeriod) {
    //         case ONE_MINUTE:
    //             duration = Duration.minutes(1);
    //             break;
    //         case THIRTY_MINUTES:
    //             duration = Duration.minutes(30);
    //             break;
    //         case ONE_HOUR:
    //             duration = Duration.hours(1);
    //             break;
    //         case THREE_HOURS:
    //             duration = Duration.hours(3);
    //             break;
    //         case SIX_HOURS:
    //             duration = Duration.hours(6);
    //             break;
    //         case TWELVE_HOURS:
    //             duration = Duration.hours(12);
    //             break;
    //     }

    //     timeline = new Timeline(new KeyFrame(duration, event -> {
    //         updateData(currencyName, timePeriod);
    //         System.out.println(currentIndex);
    //     }));

    //     timeline.setCycleCount(Timeline.INDEFINITE);
    //     timeline.play();
    // }
    */

    public VBox getRootLayout() {
        return rootLayout;
    }

    public LineChart<Number, Number> getLineChart() {
        return lineChart;
    }
}
