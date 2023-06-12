module Main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;
    requires itextpdf;


    opens Main to javafx.fxml;
    exports Main;

    exports Controller;
    opens Controller to javafx.fxml;

    opens Models to javafx.base;

    exports Controller.Extra;
    opens Controller.Extra to javafx.fxml;
}