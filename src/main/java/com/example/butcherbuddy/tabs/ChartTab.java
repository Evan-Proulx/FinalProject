package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.UpdateTables;
import com.example.butcherbuddy.pojo.CustomerItem;
import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.tables.CustomerItemsTable;
import com.example.butcherbuddy.tables.InventoryTable;
import com.example.butcherbuddy.tables.ProductTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChartTab extends Tab {
    private static ChartTab instance;
    private PieChart chart;
    InventoryTable inventoryTable = InventoryTable.getInstance();
    ProductTable productTable = ProductTable.getInstance();
    CustomerItemsTable customerItemsTable = CustomerItemsTable.getInstance();
    ArrayList<Inventory> inventories = inventoryTable.getAllInventories();
    ArrayList<CustomerItem> customerItems = customerItemsTable.getAllCustomerItems();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);


    private ChartTab() {
        BorderPane root = new BorderPane();

        xAxis.setLabel("Products");
        yAxis.setLabel("Amount");

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(45);
        barChart.setCategoryGap(20);

        barChart.setTitle("Customer Orders and Inventory");
        barChart.setVisible(true);

        chart = new PieChart();
        chart.setTitle("All inventory Products");
        chart.setLabelsVisible(true);

        root.setCenter(barChart);
        createOrderGraph();

        Button refresh = new Button("Refresh");
        ToggleButton toggleChartBtn = new ToggleButton("Toggle Chart");
        toggleChartBtn.getStyleClass().add("toggle-switch");
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(refresh, toggleChartBtn);

        //Recalls the currently selected chart
        refresh.setOnAction(e -> {
            inventories = inventoryTable.getAllInventories();
            customerItems = customerItemsTable.getAllCustomerItems();
            if (toggleChartBtn.isSelected()) {
                createInventoryChart();
            } else {
                createOrderGraph();
            }
        });

        //toggles between chart styles
        toggleChartBtn.setOnAction(event -> {
            if (toggleChartBtn.isSelected()) {
                root.setCenter(chart);
                createInventoryChart();
            } else {
                root.setCenter(barChart);
                createOrderGraph();
            }
        });

        root.setBottom(buttonHBox);
        this.setText("Stats");
        this.setContent(root);
    }




    //Takes all records from inventory table and displays product name and quantity in pie chart
    public void createInventoryChart() {
        ArrayList<PieChart.Data> data = new ArrayList<>();
        for (Inventory inventory : inventories) {
            int id = inventory.getProductId();
            data.add(new PieChart.Data(productTable.getProduct(id).getName() + " (" +inventory.getQuantity() + ")", inventory.getQuantity()));
        }
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
        chart.setData(chartData);
    }


    //Takes all records from inventory table and Customer order table and displays them together sorted by product
    public void createOrderGraph() {
        barChart.getData().clear();

        XYChart.Series<String, Number> orderDataSeries = new XYChart.Series<>();
        orderDataSeries.setName("Customer Orders");
        XYChart.Series<String, Number> inventoryDataSeries = new XYChart.Series<>();
        inventoryDataSeries.setName("Inventory Records");

        barChart.setCategoryGap(40);

        Map<Integer, Double> itemMap = new HashMap<>();
        for (CustomerItem item : customerItems) {
            int id = item.getProductId();
            double quantity = item.getQuantity();
            //Adds items with matching ids together in the map
            itemMap.merge(id, quantity, Double::sum);
        }
        //Adds items in map to the chart
        itemMap.forEach((id, totalQuantity) -> {
            orderDataSeries.getData().add(new XYChart.Data<>(productTable.getProduct(id).getName(), totalQuantity));
        });

        //Adds each inventory record to the chart
        for (Inventory inventory : inventories) {
            int id = inventory.getProductId();
            inventoryDataSeries.getData().add(new XYChart.Data<>(productTable.getProduct(id).getName(), inventory.getQuantity()));
        }

        barChart.getData().add(inventoryDataSeries);
        barChart.getData().add(orderDataSeries);
    }


    public static ChartTab getInstance() {
        if (instance == null) {
            instance = new ChartTab();
        }
        return instance;
    }
}
