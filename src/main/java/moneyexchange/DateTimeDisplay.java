package moneyexchange;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeDisplay {

    private Label dateTimeLabel;

    public DateTimeDisplay() {
        dateTimeLabel = new Label();
        dateTimeLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #7e04a6;");
        updateDateTimeLabel();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabel()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateDateTimeLabel() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        dateTimeLabel.setText(now.format(formatter));
    }

    public Label getDateTimeLabel() {
        return dateTimeLabel;
    }
}

