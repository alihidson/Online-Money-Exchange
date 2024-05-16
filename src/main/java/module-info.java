module moneyexchange.moneyexchange {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens moneyexchange.moneyexchange to javafx.fxml;
    exports moneyexchange;
}