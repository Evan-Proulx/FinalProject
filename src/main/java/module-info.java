module com.example.butcherbuddy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.butcherbuddy.pojo to javafx.base;
    exports com.example.butcherbuddy;
    exports com.example.butcherbuddy.panes;
    opens com.example.butcherbuddy.panes to javafx.fxml;
    exports com.example.butcherbuddy.scenes;
    opens com.example.butcherbuddy.scenes to javafx.fxml;
}