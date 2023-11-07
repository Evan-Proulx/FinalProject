package com.example.butcherbuddy;

import com.example.butcherbuddy.scenes.MenuScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class LaunchApp extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("Butcher Buddy");
        mainStage.setScene(new MenuScene());
        mainStage.show();
        mainStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch();
    }

}
