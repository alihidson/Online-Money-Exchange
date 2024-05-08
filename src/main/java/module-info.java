module moneyexchange.moneyexchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens moneyexchange.moneyexchange to javafx.fxml;
    exports moneyexchange;
}