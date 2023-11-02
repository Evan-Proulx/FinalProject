package com.example.butcherbuddy.panes;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class LoginPane extends BorderPane {
    public LoginPane(){
        this.getStylesheets().add("com.example.butcherbuddy/style.css");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getStyleClass().add("background");

        Text title = new Text("Butcher Buddy");
        title.setFill(Color.WHITE);
        title.getStyleClass().add("title-text");
        VBox.setMargin(title, new Insets(80, 0, 80, 0)); // Insets: top, right, bottom, left

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

        Button submitButton = new Button("Login");
        submitButton.setDefaultButton(true);
        submitButton.getStyleClass().add("button-style");

        vBox.getChildren().addAll(title,usernameLabel,userName,passwordLabel,password,submitButton);
        this.setCenter(vBox);


        EventHandler<ActionEvent> submitHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String usernameInput = userName.getText();
                String passwordInput = password.getText();
                System.out.println("Username: " + usernameInput + "\n" +
                        "Password: " + passwordInput);

                if(usernameInput.equals("username") && passwordInput.equals("1234")){
                    title.setText(":)");
                }
            }
        };

        submitButton.setOnAction(submitHandler);





    }
}
