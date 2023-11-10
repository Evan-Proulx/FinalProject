package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class OrderFormPane extends BorderPane {
    CategoryTable categoryTable = new CategoryTable();
    ProductTable productTable = new ProductTable();
    OrdersTable ordersTable = new OrdersTable();
    OrderItemsTable orderItemsTable = new OrderItemsTable();
    InventoryTable inventoryTable = new InventoryTable();

    public OrderFormPane(){
        this.getStylesheets().add("com.example.butcherbuddy/style.css");
        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);

        Button newItem = new Button("Add Item");
        vBox.getChildren().add(newItem);

        newItem.setOnMouseClicked(event -> {
            addNewItem(vBox);
        });


        this.setCenter(vBox);
    }
    private void addNewItem(VBox container){
        VBox infoGroup = new VBox();
        infoGroup.setAlignment(Pos.CENTER);

        Text title = new Text("Create an Order");
        Text product = new Text("Product:");
        ComboBox<Product> comboProduct = new ComboBox<>();
        comboProduct.setItems(FXCollections.observableArrayList(productTable.getAllProducts()));
        comboProduct.getSelectionModel().select(0);
        infoGroup.getChildren().addAll(title,product, comboProduct);

        Text quantity = new Text("Quantity: ");
        Spinner<Integer> numberInput = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0, 1);
        numberInput.setValueFactory(valueFactory);
        infoGroup.getChildren().addAll(quantity,numberInput);

        container.setAlignment(Pos.TOP_CENTER);
        container.getChildren().add(infoGroup);
    }
}
