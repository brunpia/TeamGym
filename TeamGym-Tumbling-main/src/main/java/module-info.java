module teamgym.app {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens teamgym to javafx.fxml;
    exports teamgym;
}