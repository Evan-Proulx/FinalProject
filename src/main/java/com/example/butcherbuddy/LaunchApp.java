package com.example.butcherbuddy;

import com.example.butcherbuddy.scenes.CreditsScene;
import com.example.butcherbuddy.scenes.LoginScene;
import com.example.butcherbuddy.scenes.MenuScene;
import com.example.butcherbuddy.scenes.OrderFormScene;
import javafx.application.Application;
import javafx.stage.Stage;

public class LaunchApp extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("Butcher Buddy");
        mainStage.setScene(new LoginScene());
        mainStage.show();
        mainStage.setResizable(false);
    }
    public static void main(String[] args) {
        launch();
    }

}
