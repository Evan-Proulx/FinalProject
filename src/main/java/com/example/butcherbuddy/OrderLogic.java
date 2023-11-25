package com.example.butcherbuddy;

import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.ProductTable;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderLogic {

    ProductTable productTable = new ProductTable();


    //Arraylists that hold the comboboxes and spinners of each item
    private ArrayList<ComboBox<Product>> productList = new ArrayList<>();
    private ArrayList<Spinner<Integer>> quantityList = new ArrayList<>();
    //HashMap holds The product as key and quantity as value of each item
    private Map<Product, Integer> itemMap = new HashMap<>();

    public void addNewItem(VBox container) {
        VBox infoGroup = new VBox();
        infoGroup.setAlignment(Pos.CENTER);
        infoGroup.setSpacing(10);
        infoGroup.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Text title = new Text("Create an Order");
        Text product = new Text("Product:");
        ComboBox<Product> comboProduct = new ComboBox<>();
        comboProduct.setItems(FXCollections.observableArrayList(productTable.getAllProducts()));
        comboProduct.getSelectionModel().select(0);
        infoGroup.getChildren().addAll(title, product, comboProduct);

        Text quantity = new Text("Quantity: ");
        Spinner<Integer> numberInput = new Spinner<>();
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 0, 1);
        numberInput.setValueFactory(valueFactory);
        infoGroup.getChildren().addAll(quantity, numberInput);

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
    public Map<Product, Integer> accessInputValues() {
        for (int i = 0; i < productList.size(); i++) {
            ComboBox<Product> comboProduct = productList.get(i);
            Product selectedProduct = comboProduct.getValue();

            Integer selectedQuantity = quantityList.get(i).getValue();

            itemMap.put(selectedProduct, selectedQuantity);
        }
        for (Map.Entry<Product, Integer> entry : itemMap.entrySet()) {
            System.out.println("Product: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
        return itemMap;
    }
}
