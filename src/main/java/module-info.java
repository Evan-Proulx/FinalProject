module com.example.butcherbuddy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.butcherbuddy to javafx.fxml;
    exports com.example.butcherbuddy;
    exports com.example.butcherbuddy.panes;
    opens com.example.butcherbuddy.panes to javafx.fxml;
    exports com.example.butcherbuddy.scenes;
    opens com.example.butcherbuddy.scenes to javafx.fxml;
}