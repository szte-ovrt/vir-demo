module hu.sed {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires pdfbox;

    opens hu.sed to javafx.fxml;
    exports hu.sed;
}
