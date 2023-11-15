package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.LaunchApp;
import com.example.butcherbuddy.pojo.Login;
import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.scenes.MenuScene;
import com.example.butcherbuddy.scenes.OrderFormScene;
import com.example.butcherbuddy.tables.*;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class LoginPane extends BorderPane {
    public LoginPane(){
        this.getStylesheets().add("com.example.butcherbuddy/style.css");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getStyleClass().add("background");


        System.out.println("CREATING TABLE");
        CategoryTable categoryTable = new CategoryTable();
        ProductTable productTable = new ProductTable();
        OrdersTable ordersTable = new OrdersTable();
        OrderItemsTable orderItemsTable = new OrderItemsTable();
        InventoryTable inventoryTable = new InventoryTable();


        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
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

        Button submitButton = new Button("Login or Sign Up");
        submitButton.setDefaultButton(true);
        submitButton.getStyleClass().add("button-style");

        Image knife = new Image("com.example.butcherbuddy/images/Knife.png");
        Image knife2 = new Image("com.example.butcherbuddy/images/Knife2.png");

        ImageView imageView = new ImageView();
        ImageView imageView2 = new ImageView();


        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
        imageView.setImage(knife);
        imageView2.setFitHeight(300);
        imageView2.setFitWidth(200);
        imageView2.setImage(knife2);

        hBox.getChildren().addAll(imageView, imageView2);
        vBox.getChildren().addAll(title,usernameLabel,userName,passwordLabel,password,submitButton, hBox);
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
                String usernameInput = userName.getText();
                String passwordInput = password.getText();
                LoginsTable loginsTable = new LoginsTable();

                Login user = loginsTable.getLogin(usernameInput, passwordInput);

                if (!usernameInput.isEmpty() && !passwordInput.isEmpty()) { //if input textfields are not blank
                    System.out.println("usernameInput: " + usernameInput + " | passwordInput: " + passwordInput);
                    if (user == null) { //if user is NOT created in database
                        System.out.println("User is not verified in database... creating account..");
                        Login login = new Login(usernameInput, passwordInput);
                        loginsTable.createLogin(login);
                    } else {
                        System.out.println("Hello " + user.getUsername() + "! And welcome to ButcherBuddy");
                        System.out.println("-----------\nDatabase Information:" + user);
                        LaunchApp.mainStage.setScene(new OrderFormScene());
                    }
                } else {
                    System.out.println("Input text in proper text fields to create a login");
                }

            }
        };

        submitButton.setOnAction(submitHandler);





    }
}
