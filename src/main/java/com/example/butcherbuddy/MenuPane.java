package com.example.butcherbuddy;

import com.example.projectclick.Const;
import com.example.projectclick.StartGame;
import com.example.projectclick.scenes.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class MenuPane extends BorderPane {

    String btnSound = null;
    int i = 0;

    public MenuPane() {
        Text projectName = new Text("Butcher Buddy");
        Button addButton = new Button("Add to Database");
        Button removeButton = new Button("Remove From Database");
        Button exitButton = new Button("Exit");
        Button statsButton = new Button("Statistics");

        this.setLeft(statsButton);


        TextField typeText = new TextField();
        typeText.setPromptText("Type:");
        typeText.getStyleClass().add("text-field");

        TextField qtyText = new TextField();
        qtyText.setPromptText("QTY:");
        qtyText.getStyleClass().add("text-field");


        addButton.setOnMouseClicked(e->{
            System.out.println("Add Button Clicked");
        });

        removeButton.setOnMouseClicked(event -> {
            System.out.println("Remove Button Clicked");
        });

        statsButton.setOnMouseClicked(event -> {
            System.out.println("Statistics Button Clicked");
        });

        exitButton.setOnMouseClicked(e->{
            Platform.exit();
        });




        VBox buttonVBox = new VBox(10, addButton, removeButton, exitButton);
        buttonVBox.setAlignment(Pos.BASELINE_CENTER);

        VBox buttonVBox2 = new VBox(10,typeText, qtyText);
        buttonVBox2.setAlignment(Pos.BASELINE_CENTER);

        HBox buttonsHbox = new HBox(10, buttonVBox, buttonVBox2);
        buttonsHbox.setAlignment(Pos.BASELINE_CENTER);
        this.setBottom(buttonsHbox);

        //slowFade(Duration.millis(2000), companyName).play();
    }
}
