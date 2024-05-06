module moneyexchange.moneyexchange {
    requires javafx.controls;
    requires javafx.fxml;


    opens moneyexchange.moneyexchange to javafx.fxml;
    exports moneyexchange.moneyexchange;
}