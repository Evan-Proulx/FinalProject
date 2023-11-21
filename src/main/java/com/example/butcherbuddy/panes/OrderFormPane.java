package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.LaunchApp;
import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.pojo.Orders;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.scenes.LoginScene;
import com.example.butcherbuddy.scenes.OrderFormScene;
import com.example.butcherbuddy.tables.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderFormPane extends BorderPane {
    CategoryTable categoryTable = new CategoryTable();
    ProductTable productTable = new ProductTable();
    OrdersTable ordersTable = new OrdersTable();
    OrderItemsTable orderItemsTable = new OrderItemsTable();
    InventoryTable inventoryTable = new InventoryTable();

    public OrderFormPane(){
        this.getStylesheets().add("com.example.butcherbuddy/style.css");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.setSpacing(30);
        vBox.setLayoutX(Const.SCREEN_WIDTH);

        Button Logout = new Button("Logout");
        HBox LogoutBox = new HBox();
        LogoutBox.getChildren().addAll(Logout);
        LogoutBox.setAlignment(Pos.TOP_LEFT);

        HBox buttonHbox = new HBox();
        Button newItem = new Button("Add Item");
        Button submit = new Button("Submit Order");
        buttonHbox.getChildren().addAll(newItem,submit);
        buttonHbox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(buttonHbox);


        ScrollPane scrollPane = new ScrollPane(vBox);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToHeight(true);

        HBox hbox = new HBox(scrollPane, LogoutBox);
        hbox.setBackground(new Background(new BackgroundFill(Color.web("#18191a"), CornerRadii.EMPTY, Insets.EMPTY)));
        hbox.setAlignment(Pos.CENTER);
        hbox.setSpacing(30);

        this.setCenter(hbox);

        //sets new item to the screen on each button click
        newItem.setOnMouseClicked(event -> {
            addNewItem(vBox);
        });

        Logout.setOnMouseClicked(event -> {
            LaunchApp.mainStage.setScene(new LoginScene());
        });

        //Takes all items and sorts them into a Hashmap
        //Items in Hashmap are updated into the tables
        submit.setOnMouseClicked(event -> {
            accessInputValues();
            updateTables();
        });

    }

    //Arraylists that hold the comboboxes and spinners of each item
    private ArrayList<ComboBox<Product>> productList = new ArrayList<>();
    private ArrayList<Spinner<Integer>> quantityList = new ArrayList<>();
    //HashMap holds The product as key and quantity as value of each item
    private Map<Product, Integer> itemMap = new HashMap<>();

    private void addNewItem(VBox container){
        VBox infoGroup = new VBox();
        infoGroup.setAlignment(Pos.CENTER);
        infoGroup.setSpacing(10);
        infoGroup.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Text title = new Text("Create an Order");
        Text product = new Text("Product:");
        ComboBox<Product> comboProduct = new ComboBox<>();
        comboProduct.setItems(FXCollections.observableArrayList(productTable.getAllProducts()));
        comboProduct.getSelectionModel().select(0);
        infoGroup.getChildren().addAll(title,product, comboProduct);

        Text quantity = new Text("Quantity: ");
        Spinner<Integer> numberInput = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0, 1);
        numberInput.setValueFactory(valueFactory);
        infoGroup.getChildren().addAll(quantity,numberInput);

        container.setAlignment(Pos.TOP_CENTER);
        container.getChildren().add(infoGroup);

        //Add combobox and spinner to arraylist
        productList.add(comboProduct);
        quantityList.add(numberInput);
    }


    /**
     * Loop through productList and quantityList and
     * adds their values to a HashMap with the product as the key and quantity as value
     */
    private void accessInputValues(){
        for(int i=0;i<productList.size();i++){
            ComboBox<Product> comboProduct = productList.get(i);
            Product selectedProduct = comboProduct.getValue();

            Integer selectedQuantity = quantityList.get(i).getValue();

            itemMap.put(selectedProduct, selectedQuantity);
        }
        for(Map.Entry<Product, Integer> entry : itemMap.entrySet()){
            System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }


    /**
     * Create the order table
     * The createOrder method returns the tables id
     *loop through the itemMap and set values for the key and value
     * We create a new order item for each map entry
     */
    private void updateTables(){
        long dateInMillis = System.currentTimeMillis();
        Date todayDate = new Date(dateInMillis);
        Orders order = new Orders(todayDate);
        int orderId = ordersTable.createOrder(order);

        for(Map.Entry<Product, Integer> entry : itemMap.entrySet()){
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            double price = quantity*product.getPrice();
            System.out.println(orderId + " " + product.getId() + " " + quantity + " " + price);

            OrderItem orderItem = new OrderItem(
                    orderId,
                    product.getId(),
                    quantity,
                    price
            );
            orderItemsTable.createOrderItem(orderItem);
        }
    }
}
