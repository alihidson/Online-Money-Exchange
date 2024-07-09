package moneyexchange.Currency;

import java.io.InputStream;
import java.time.LocalTime;

import moneyexchange.CurrencyName;
import moneyexchange.LoginSignupPage;
import moneyexchange.PriceVsTime;
import moneyexchange.Trading_Exchange.TradingClientUI;
import moneyexchange.Trading_Swap.SwapUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class CurrencyUI extends Currency {
    private HBox rootLayout = new HBox();
    private PriceVsTime chart;
    public int rank = 5;
    Timeline timeline;

    // *** left half ***//
    private VBox leftHalf = new VBox();
    private BorderPane chatPane = new BorderPane();

    // header
    private HBox topSection = new HBox();

    private Image iconImage;
    private ImageView currencyIcon;

    private Label nameLabel;

    private Image rankImage;
    private ImageView rankIcon;
    private Label rankLabel;
    private StackPane rankPane = new StackPane();

    private Label changLabel;

    // information section
    private GridPane infoSection = new GridPane();
    private Label currentValueLabel;
    private Label maxValueLabel;
    private Label minValueLabel;
    private Label dateOfIssueLabel;

    private VBox chartBox;

    // ** right half **//
    private VBox rightHalf = new VBox();

    private TradingClientUI clientUI;
    private SwapUI swapUI;
    private VBox tradeBox;
    private VBox swapBox;

    private Image exchanImage;
    private ImageView exchangeIcon;

    private Image swapImage;
    private ImageView swapIcon;

    // **** CONSTRUCTOR **** //
    public CurrencyUI(CurrencyName currencyName) {
        super(currencyName);

        chart = new PriceVsTime(currencyName);
        chartBox = chart.getRootLayout();
        chartBox.setMaxWidth(750);
        chartBox.setMaxHeight(500);

        setCurrencyIcon(currencyName);
        setrankIcon(rank);
        setChangeLabel();
        createHeader();
        setRightHalfIcones();
        createRightHalf();
        createInfoSection();


        chatPane.setTop(topSection);
        chatPane.setCenter(infoSection);

        leftHalf.setPadding(new Insets(10));
        leftHalf.setSpacing(10);
        leftHalf.getChildren().addAll(chatPane, chartBox);

        rightHalf.setPadding(new Insets(10));
        rightHalf.setSpacing(10);

        rootLayout.getChildren().addAll(leftHalf, rightHalf);
        startAutoUpdate(currencyName);
    }

    public void startAutoUpdate(CurrencyName currencyName) {
        if (timeline != null) {
            timeline.stop();
        }

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
                    Platform.runLater(() -> {
                        time = LocalTime.now();
                        updateData(time);
                        updateDataUI();
                    });
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

    public void updateDataUI() {
        setChangeLabel();
        maxValueLabel.setText(String.valueOf(maxValue));
        minValueLabel.setText(String.valueOf(minValue));
        currentValueLabel.setText(String.valueOf(currentValue));
    }

    // Rest of the code remains the same...

    // create header
    private void setCurrencyIcon(CurrencyName currencyName) {
        String path = "/icon/currency";
        switch (currencyName) {
            case USD:
                path += "/usd/usd-96.png";
                break;
            case EUR:
                path += "/eur/eur-60.png";
                break;
            case TOMAN:
                path += "/toman/Toman.png";
                break;
            case YEN:
                path += "/yen/yen-96.png";
                break;
            case GBP:
                path += "/gbp/gbp-96.png";
                break;
        }

        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream != null) {
            iconImage = new Image(inputStream);
            currencyIcon = new ImageView(iconImage);
        } else {
            System.err.println("Failed to load currency icon for: " + currencyName);
            currencyIcon = new ImageView(); // default or empty image view
        }

        currencyIcon.setFitWidth(40);
        currencyIcon.setFitHeight(40);
    }

    private void setrankIcon(int rank) {
        String path = "/icon/label";

        switch (rank) {
            case 1:
                path += "/purple/purple-96.png";
                break;
            case 2:
                path += "/blue/blue-96.png";
                break;
            case 3:
                path += "/jadeGreen/jade-green-96.png";
                break;
            case 4:
                path += "/yellow/yellow-96.png";
                break;
            case 5:
                path += "/red/red-96.png";
                break;
        }

        InputStream inputStream = getClass().getResourceAsStream(path);
        if (inputStream != null) {
            rankImage = new Image(inputStream);
            rankIcon = new ImageView(rankImage);
            rankIcon.setFitWidth(60);
            rankIcon.setFitHeight(60);

            rankLabel = new Label("No. " + rank);
            rankLabel.setFont(new Font("Arial", 14));
            rankLabel.setTextFill(Color.WHITE);

            rankPane.getChildren().addAll(rankIcon, rankLabel);
            StackPane.setAlignment(rankLabel, Pos.CENTER);
        }
        else {
            System.err.println("Failed to load image for rank " + rank + " at path: " + path);
            rankIcon = new ImageView(); // default image
        }
    }

    private void setRightHalfIcones(){
        InputStream inputStream1 = getClass().getResourceAsStream("/icon/exchange-icon.PNG");
        InputStream inputStream2 = getClass().getResourceAsStream("/icon/swap-icon.PNG");

        exchanImage = new Image(inputStream1);
        exchangeIcon = new ImageView(exchanImage);
        exchangeIcon.setFitWidth(96);
        exchangeIcon.setFitHeight(96);

        exchangeIcon.setOnMouseClicked(event -> showTradeBox());

        swapImage = new Image(inputStream2);
        swapIcon = new ImageView(swapImage);
        swapIcon.setFitWidth(96);
        swapIcon.setFitHeight(96);

        swapIcon.setOnMouseClicked(event -> showSwapBox());
    }

    private void setChangeLabel() {
        changLabel = new Label(Float.toString(change));

        if (nameString.charAt(0) == '+') {
            changLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
        }
        else if (nameString.charAt(0) == '-'){
            changLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
        }
        else {
            changLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16px;");
        }
    }

    private void createHeader() {
        nameLabel = new Label(nameString);
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        nameLabel.setTextFill(Color.DARKSLATEGRAY);

        topSection.setSpacing(20);
        topSection.setPadding(new Insets(10));
        topSection.setAlignment(Pos.CENTER_LEFT);

        topSection.getChildren().addAll(rankPane, currencyIcon, nameLabel, changLabel);
    }

    // create information section
    private void createInfoSection() {
        LocalTime currentTime = LocalTime.now();

        Label cv = new Label("Current Value: ");
        cv.setStyle("-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #00897B;" + // Changed color to a darker blue
                "-fx-padding: 5 10 5 10;" + // Added padding inside the label
                "-fx-border-radius: 5;" + // Added border radius for rounded corners
                "-fx-background-radius: 5;"); // Added background radius for rounded corners

        Label maxv = new Label("Max Value: ");
        maxv.setStyle("-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #00897B;" +
                "-fx-padding: 5 10 5 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;");

        Label minv = new Label("Min Value: ");
        minv.setStyle("-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #00897B;" +
                "-fx-padding: 5 10 5 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;");

        Label doi = new Label("Date of Issue: ");
        doi.setStyle("-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-background-color: #00897B;" +
                "-fx-padding: 5 10 5 10;" +
                "-fx-border-radius: 5;" +
                "-fx-background-radius: 5;");

        currentValueLabel = new Label(Float.toString(currentValue));
        maxValueLabel = new Label(Float.toString(maxValue));
        minValueLabel = new Label(Float.toString(minValue));
        dateOfIssueLabel = new Label(String.valueOf(dateOfIssue));

        infoSection.setPadding(new Insets(10));
        infoSection.setVgap(10);
        infoSection.setHgap(10);

        infoSection.add(cv, 0, 0);
        infoSection.add(currentValueLabel, 1, 0);

        infoSection.add(maxv, 0, 1);
        infoSection.add(maxValueLabel, 1, 1);

        infoSection.add(minv, 0, 2);
        infoSection.add(minValueLabel, 1, 2);

        infoSection.add(doi, 0, 3);
        infoSection.add(dateOfIssueLabel, 1, 3);

        infoSection.add(exchangeIcon, 3, 0, 1, 3);
        infoSection.add(swapIcon, 4, 0, 1, 3);
    }

    private void showTradeBox() {
        rightHalf.getChildren().remove(tradeBox); // Remove tradeBox if already added
        rightHalf.getChildren().remove(swapBox); // Remove swapBox if already added
        tradeBox.setVisible(true);
        rightHalf.getChildren().add(tradeBox); // Add tradeBox to rightHalf
    }

    private void hideTradeBox() {
        rightHalf.getChildren().remove(tradeBox); // Remove tradeBox from rightHalf
    }

    private void showSwapBox() {
        rightHalf.getChildren().remove(tradeBox); // Remove tradeBox if already added
        rightHalf.getChildren().remove(swapBox); // Remove swapBox if already added
        swapBox.setVisible(true);
        rightHalf.getChildren().add(swapBox); // Add swapBox to rightHalf
    }

    private void hideSwapeBox() {
        rightHalf.getChildren().remove(swapBox); // Remove swapBox from rightHalf
    }


    private void createRightHalf() {
        rightHalf.getChildren().addAll(exchangeIcon, swapIcon);

        clientUI = new TradingClientUI(LoginSignupPage.firstNameProf, LoginSignupPage.UserName, name);
        swapUI = new SwapUI();

        tradeBox = clientUI.getRoot();
        tradeBox.setPadding(new Insets(20));
        tradeBox.setSpacing(10);

        Button exitButton1 = new Button("Exit");
        exitButton1.setOnAction(e -> hideTradeBox());
        tradeBox.getChildren().add(exitButton1);

        swapBox = swapUI.getLayout();
        swapBox.setPadding(new Insets(20));
        swapBox.setSpacing(10);

        Button exitButton2 = new Button("Exit");
        exitButton2.setOnAction(e -> hideSwapeBox());
        swapBox.getChildren().add(exitButton2);
    }

    public HBox getRootLayout() {
        return rootLayout;
    }
}
