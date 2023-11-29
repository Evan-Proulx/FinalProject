package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.pojo.Category;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.CategoryTable;
import com.example.butcherbuddy.tables.ProductTable;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;

import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class AddProductTab extends Tab {
    private static AddProductTab instance;
    CategoryTable categoryTable = CategoryTable.getInstance();
    ProductTable productTable = ProductTable.getInstance();

    private AddProductTab() {
        Label nameLabel = new Label("Name");
        nameLabel.getStyleClass().add("label-text");
        TextField nameTextField = new TextField();
        VBox textFieldVbox = new VBox(15);
        textFieldVbox.getChildren().addAll(nameLabel, nameTextField);
        textFieldVbox.setAlignment(Pos.CENTER);

        Label categoryLabel = new Label("Category");
        categoryLabel.getStyleClass().add("label-text");
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
        categoryComboBox.getSelectionModel().select(0);
        VBox comboVbox = new VBox(15);
        comboVbox.getChildren().addAll(categoryLabel, categoryComboBox);
        comboVbox.setAlignment(Pos.CENTER);

        HBox hBox = new HBox(60);
        hBox.getChildren().addAll(textFieldVbox, comboVbox);
        hBox.setAlignment(Pos.CENTER);

        Label priceLabel = new Label("Price");
        nameLabel.getStyleClass().add("label-text");
        TextField priceTextField = new TextField();
        VBox priceFieldVbox = new VBox(15);
        priceFieldVbox.getChildren().addAll(priceLabel, priceTextField);
        priceFieldVbox.setAlignment(Pos.CENTER);

        Label alertLabel = new Label("");
        alertLabel.getStyleClass().add("alert-text");
        alertLabel.setVisible(false);

        Button submit = new Button("Add Product");

        // Create layout
        VBox root = new VBox(80);
        root.getChildren().addAll(hBox,priceFieldVbox, alertLabel,submit);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        this.setText("Add Products");
        this.setContent(root);

        submit.setOnAction(event -> {
            String productName = nameTextField.getText();
            Category category = categoryComboBox.getValue();
            String productPrice = priceTextField.getText();

            Product product = checkValidValues(productName, productPrice, category);

            if (product != null){
                productTable.createProduct(product);
            }
            else {
                alertLabel.setText("Invalid input! Try again");
                alertLabel.setVisible(true);
            }
        });

    }

    public static AddProductTab getInstance() {
        if (instance == null) {
            instance = new AddProductTab();
        }
        return instance;
    }

    //Check if values in the textfields are valid
    //If so returns a Product object
    public Product checkValidValues(String name, String price, Category category){
        try {
            double priceValue = Double.parseDouble(price);
            if (!name.isEmpty()) {
                return new Product(name, priceValue, category.getId());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
        return null;
    }

}
