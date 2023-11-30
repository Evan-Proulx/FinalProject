package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.OrderLogic;
import com.example.butcherbuddy.pojo.Category;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import java.util.Optional;

public class ManageProductTab extends Tab {

    OrderLogic orderLogic = new OrderLogic();
    private static ManageProductTab instance;
    CategoryTable categoryTable = CategoryTable.getInstance();
    ProductTable productTable = ProductTable.getInstance();
    InventoryTable inventoryTable = InventoryTable.getInstance();
    OrderItemsTable orderItemsTable = OrderItemsTable.getInstance();
    CustomerItemsTable customerItemsTable = CustomerItemsTable.getInstance();

    private ManageProductTab() {

        Label nameLabel = new Label("Name");
        nameLabel.getStyleClass().add("label-text");
        TextField nameTextField = new TextField();
        VBox textFieldVbox = new VBox();
        textFieldVbox.getChildren().addAll(nameLabel, nameTextField);

        Label categoryLabel = new Label("Category");
        categoryLabel.getStyleClass().add("label-text");
        ComboBox<Category> categoryComboBox = new ComboBox<>();
        categoryComboBox.setItems(FXCollections.observableArrayList(categoryTable.getAllCategories()));
        categoryComboBox.getSelectionModel().select(0);
        VBox comboVbox = new VBox(15);
        comboVbox.getChildren().addAll(categoryLabel, categoryComboBox);

        HBox categoryHbox = new HBox(20);
        categoryHbox.getChildren().addAll(textFieldVbox, comboVbox);
        categoryHbox.setAlignment(Pos.CENTER);

        Label priceLabel = new Label("Price");
        priceLabel.getStyleClass().add("label-text");
        TextField priceTextField = new TextField();
//        VBox priceFieldVbox = new VBox();
//        priceFieldVbox.getChildren().addAll(priceLabel, priceTextField);

        Text alertLabel = new Text("");
        alertLabel.getStyleClass().add("alert-text");
        alertLabel.setVisible(false);

        Button submit = new Button("Add Product");

        Label addItemLabel = new Label("Add Product");
        addItemLabel.getStyleClass().add("header-text");

        // Create layout
        VBox addItemForm = new VBox(10);
        addItemForm.getChildren().addAll(addItemLabel, categoryHbox, priceLabel, priceTextField, alertLabel, submit);
        addItemForm.setAlignment(Pos.CENTER);


        //Delete Item form
        Label productLabel = new Label("Products");
        productLabel.getStyleClass().add("label-text");
        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setItems(FXCollections.observableArrayList(productTable.getAllProducts()));
        productComboBox.getSelectionModel().select(0);
//        VBox productCombo = new VBox(15);
//        productCombo.getChildren().addAll(productLabel, productComboBox);

        Button deleteBtn = new Button("Delete Item");

        Label deleteItemLabel = new Label("Delete A Product");
        deleteItemLabel.getStyleClass().add("header-text");

        VBox deleteItemForm = new VBox(20);
        deleteItemForm.getChildren().addAll(deleteItemLabel, productLabel, productComboBox, deleteBtn);
        deleteItemForm.setAlignment(Pos.CENTER);

        VBox root = new VBox(40);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        root.getChildren().addAll(addItemForm, deleteItemForm);

        this.setText("Manage Products");
        this.setContent(root);

        submit.setOnAction(event -> {
            String productName = nameTextField.getText();
            Category category = categoryComboBox.getValue();
            String productPrice = priceTextField.getText();

            Product product = checkValidValues(productName, productPrice, category);

            if (product != null) {
                productTable.createProduct(product);
                orderLogic.alert("accept", "Product added to products database!", alertLabel);
            } else {
                orderLogic.alert("error", "Invalid input! Try again", alertLabel);
                alertLabel.setVisible(true);
            }
        });

        deleteBtn.setOnAction(event -> {
            Product selectedProduct = productComboBox.getValue();

            handleDelete(selectedProduct);
        });

    }

    public static ManageProductTab getInstance() {
        if (instance == null) {
            instance = new ManageProductTab();
        }
        return instance;
    }



    //Check if values in the textfields are valid
    //If so returns a Product object
    public Product checkValidValues(String name, String price, Category category) {
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


    //Makes the user confirm they want to delete the record
    private boolean showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("DELETE ITEM ALERT");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this record? (All records of this product will be deleted from the inventory!)\n" +
                "You wont be able to undo this action.");

        ButtonType confirmButton = new ButtonType("Confirm");
        ButtonType stopButton = new ButtonType("cancel");

        // Add buttons to the dialog
        alert.getButtonTypes().setAll(confirmButton, stopButton);

        // Show the dialog and wait for user's response
        Optional<ButtonType> result = alert.showAndWait();

        alert.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == confirmButton) {
            return true;
        } else {
            return false;
        }
    }

    //Calls the alert function to check if the user wants to deleted
    //gets inventory record from productId and deletes record from inventory and product tables
    private void handleDelete(Product product){
        Inventory inventoryToDelete = inventoryTable.getInventory(product.getId());
        boolean result = showAlert();
        if (result){
            if (inventoryToDelete != null){
                inventoryTable.deleteInventory(inventoryToDelete);
                orderItemsTable.deleteAllOfProduct(product);
                customerItemsTable.deleteAllOfProduct(product);
            }
            productTable.deleteProduct(product);
        }
    }

}
