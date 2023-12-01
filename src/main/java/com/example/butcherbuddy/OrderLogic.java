package com.example.butcherbuddy;

import com.example.butcherbuddy.pojo.*;
import com.example.butcherbuddy.tables.CategoryTable;
import com.example.butcherbuddy.tables.InventoryTable;
import com.example.butcherbuddy.tables.ProductTable;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderLogic {

    ProductTable productTable = ProductTable.getInstance();

    CategoryTable categoryTable = CategoryTable.getInstance();
    InventoryTable inventoryTable = InventoryTable.getInstance();

    ArrayList<NamedInventory> newInventory;
    ArrayList<NamedCategory> newCategory;

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
        infoGroup.getStyleClass().add("vbox");

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


    //Gets all items from the inventory table and converts them into NamedInventory objects
    //Now Items in the tableView have names instead of productIds
    public ArrayList<NamedInventory> getNamedInventory(){
        ArrayList<Inventory> inventories = inventoryTable.getAllInventories();
        newInventory = new ArrayList<>();

        for (Inventory inventory : inventories){
            int id = inventory.getProductId();
            String productName = productTable.getProduct(id).getName();
            System.out.println(productName);

            NamedInventory namedInventoryItem = new NamedInventory(productName, inventory.getQuantity(), inventory.getTotalPrice());
            newInventory.add(namedInventoryItem);
            System.out.println("Named Inventory: " + namedInventoryItem);
        }
        return newInventory;
    }

    //Gets all items from the inventory table and converts them into NamedCategory objects
    //Now Items in the tableView have names and categories
    public ArrayList<NamedCategory> getNamedCategory(){
        ArrayList<Inventory> inventories = inventoryTable.getAllInventories();
        ArrayList<Category> categories = categoryTable.getAllCategories();
        ArrayList<Product> products = productTable.getAllProducts();

        newCategory = new ArrayList<>();
        for (Product product : products){
            int id = product.getId();
            String productName = productTable.getProduct(id).getName();
            System.out.println(productName);

            NamedCategory namedInventoryItem = new NamedCategory(product.getCategory(), productName, product.getPrice());
            newCategory.add(namedInventoryItem);
            System.out.println("Named Inventory: " + namedInventoryItem);
        }
        return newCategory;
    }


    public void alert(String alert, String text, Text container) {
        if (alert.equals("error")){
            container.setFill(Color.RED);
            container.setText(text);
        } else if (alert.equals("accept")) {
            container.setFill(Color.GREEN);
            container.setText(text);

        }

    }



}
