package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.LaunchApp;
import com.example.butcherbuddy.scenes.TabHostScene;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseLoginPane extends BorderPane {
    private Connection connection = null;

    String DBC = "src/main/resources/com.example.butcherbuddy/DBC.txt";


    public DatabaseLoginPane(){




        this.getStylesheets().add("com.example.butcherbuddy/style.css");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));


        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);

        Text title = new Text("Butcher Buddy");
        title.setFill(Color.WHITE);
        title.getStyleClass().add("title-text");
        VBox.setMargin(title, new Insets(80, 0, 80, 0)); // Insets: top, right, bottom, left

        Text databaseLocationLabel = new Text("Enter Database Location");
        databaseLocationLabel.setFill(Color.WHITE);
        databaseLocationLabel.getStyleClass().add("label-text");

        TextField databaseLocation = new TextField();
        databaseLocation.setPromptText("Enter text here");
        databaseLocation.getStyleClass().add("text-field");

        Text usernameLabel = new Text("Enter Username");
        usernameLabel.setFill(Color.WHITE);
        usernameLabel.getStyleClass().add("label-text");

        TextField userName = new TextField();
        userName.setPromptText("Enter text here");
        userName.getStyleClass().add("text-field");

        Text passwordLabel = new Text("Enter Password");
        passwordLabel.setFill(Color.WHITE);
        passwordLabel.getStyleClass().add("label-text");

        PasswordField password = new PasswordField();
        password.setPromptText("Enter text here");
        password.getStyleClass().add("text-field");

        Button submitButton = new Button("Connect to database");
        submitButton.setDefaultButton(true);
        submitButton.getStyleClass().add("button-style");

        Text errorLabel = new Text(":)");
        errorLabel.setOpacity(0);
        errorLabel.getStyleClass().add("label-text");

        Image knife = new Image("com.example.butcherbuddy/images/Knife.png");
        Image knife2 = new Image("com.example.butcherbuddy/images/Knife2.png");

        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();


        imageView.setFitHeight(200);
        imageView.setFitWidth(100);
        imageView.setImage(knife);
        imageView2.setFitHeight(200);
        imageView2.setFitWidth(100);
        imageView2.setImage(knife2);

        hBox.getChildren().addAll(imageView, imageView2);
        vBox.getChildren().addAll(title,databaseLocationLabel,databaseLocation,usernameLabel,userName,passwordLabel,password,submitButton,errorLabel, hBox);
        this.setCenter(vBox);


        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), imageView);
        translateTransition.setFromX(2000);
        translateTransition.setFromY(0);
        translateTransition.setToX(70);
        translateTransition.setToY(0);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(2000), imageView2);
        translateTransition2.setFromX(-2000);
        translateTransition2.setFromY(0);
        translateTransition2.setToX(-70);
        translateTransition2.setToY(0);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), title);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(.5);
        fadeTransition.setCycleCount(Timeline.INDEFINITE);
        fadeTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                translateTransition2
        );
        //parallelTransition.play();

        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                parallelTransition,
                fadeTransition

        );
        sequentialTransition.play();


        EventHandler<ActionEvent> submitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String databaseInput = databaseLocation.getText();
                String usernameInput = userName.getText();
                String passwordInput = password.getText();

                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connection = DriverManager
                            .getConnection("jdbc:mysql://localhost/"+ databaseInput +
                                            "?serverTimezone=UTC",
                                    usernameInput,
                                    passwordInput);
                    System.out.println("Connection Successfully created");
                    LaunchApp.mainStage.setScene(new TabHostScene());
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(DBC));
                        BufferedReader bufferedReader = new BufferedReader(new FileReader(DBC));
                        if ((bufferedReader.readLine()) == null) {
                            String info = databaseInput + "\n" + usernameInput + "\n" + passwordInput;
                            bufferedWriter.write(info);
                            System.out.println("stored database login:\n" + info);
                            bufferedReader.close();
                            bufferedWriter.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        submitButton.setOnAction(submitHandler);

    }
}
