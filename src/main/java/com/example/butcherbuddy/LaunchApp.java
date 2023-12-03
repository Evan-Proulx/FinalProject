package com.example.butcherbuddy;

import com.example.butcherbuddy.database.Database;
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
        String hostStored = null;

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(DBC))){
            hostStored = bufferedReader.readLine();
            databaseStored = bufferedReader.readLine();
            usernameStored = bufferedReader.readLine();
            passwordStored = bufferedReader.readLine();
            System.out.println("Attempting login with database: " + hostStored + " with user " + usernameStored + " to location " + databaseStored + " with password " + passwordStored);
            try{
                Database database = Database.getInstance();
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager
                        .getConnection("jdbc:mysql://" + hostStored + "/"+ databaseStored +
                                        "?serverTimezone=UTC",
                                usernameStored,
                                passwordStored);
                System.out.println("Connection Successfully created");
                database.setLoginInfo(hostStored,databaseStored,usernameStored,passwordStored);
                mainStage.setScene(new TabHostScene());
            }catch (Exception e){
                System.out.println(e);
                mainStage.setScene(new DatabaseLoginScene());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        launch();
    }

}
