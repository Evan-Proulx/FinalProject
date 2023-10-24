module com.example.butcherbuddy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.butcherbuddy to javafx.fxml;
    exports com.example.butcherbuddy;
}