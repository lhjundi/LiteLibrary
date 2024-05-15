module com.lhjundi {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.lhjundi to javafx.fxml;
    exports com.lhjundi;
}
