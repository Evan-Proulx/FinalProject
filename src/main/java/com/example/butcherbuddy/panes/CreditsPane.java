package com.example.butcherbuddy.panes;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CreditsPane extends BorderPane {
    public CreditsPane() {
        this.getStylesheets().add("com.example.butcherbuddy/style.css");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getStyleClass().add("background");

        Text title = new Text("Butcher Buddy");
        title.setFill(Color.WHITE);
        title.getStyleClass().add("title-text");

        Text authors = new Text("Nathan Jamrog and Evan Proulx");
        authors.getStyleClass().add("label-text");
        authors.setFill(Color.WHITE);

        Text thanks = new Text("Thank you for using our project!");
        thanks.getStyleClass().add("label-text");
        thanks.setFill(Color.WHITE);

        vBox.getChildren().addAll(title,authors,thanks);

        vBox.setAlignment(Pos.CENTER);
        this.setCenter(vBox);

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(5000), vBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(2000), title);
        translateTransition.setFromY(0);
        translateTransition.setFromX(0);
        translateTransition.setToY(-2000);
        translateTransition.setToX(0);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(2000), authors);
        translateTransition2.setFromY(0);
        translateTransition2.setFromX(0);
        translateTransition2.setToY(-2000);
        translateTransition2.setToX(0);

        TranslateTransition translateTransition3 = new TranslateTransition(Duration.millis(2000), thanks);
        translateTransition3.setFromY(0);
        translateTransition3.setFromX(0);
        translateTransition3.setToY(-2000);
        translateTransition3.setToX(0);


        SequentialTransition sequentialTransition = new SequentialTransition();
        sequentialTransition.getChildren().addAll(
                fadeTransition,
                translateTransition,
                translateTransition2,
                translateTransition3

        );
        sequentialTransition.play();




    }
}
