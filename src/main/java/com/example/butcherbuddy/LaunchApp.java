package com.example.butcherbuddy;

import com.example.butcherbuddy.pojo.Login;
import com.example.butcherbuddy.scenes.DatabaseLoginScene;
import com.example.butcherbuddy.scenes.LoginScene;
import com.example.butcherbuddy.scenes.TabHostScene;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;

public class LaunchApp extends Application {
    public static Stage mainStage;
    private Connection connection = null;
    String DBC = "src/main/resources/com.example.butcherbuddy/DBC.txt";
    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainStage.setTitle("Butcher Buddy");
        mainStage.show();
        mainStage.setResizable(false);

        String databaseStored = null;
        String usernameStored = null;
        String passwordStored = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBC))){
            databaseStored = bufferedReader.readLine();
            usernameStored = bufferedReader.readLine();
            passwordStored = bufferedReader.readLine();
            System.out.println("Attempting login with database user " + usernameStored + " to location " + databaseStored + " with password " + passwordStored);
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager
                        .getConnection("jdbc:mysql://localhost/"+ databaseStored +
                                        "?serverTimezone=UTC",
                                usernameStored,
                                passwordStored);
                System.out.println("Connection Successfully created");
                mainStage.setScene(new TabHostScene());
            }catch (Exception e){
                e.printStackTrace();
                mainStage.setScene(new DatabaseLoginScene());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch();
    }

}
