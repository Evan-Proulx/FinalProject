package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.tables.InventoryTable;
import com.example.butcherbuddy.tables.ProductTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class ChartTab extends Tab {
    private static ChartTab instance;
    private PieChart chart;
    InventoryTable inventoryTable = InventoryTable.getInstance();
    ProductTable productTable = ProductTable.getInstance();
    ArrayList<Inventory> inventories = inventoryTable.getAllInventories();

    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);


    private ChartTab(){
        this.setText("Stats");
        BorderPane root = new BorderPane();

        xAxis.setLabel("Product");
        yAxis.setLabel("Amount");

        barChart.setTitle("Simple Bar Chart Example");
        barChart.setVisible(true);


        chart = new PieChart();
        chart.setTitle("All inventory Products");
        chart.setLabelsVisible(true);
        root.setCenter(barChart);

        Button refresh = new Button("Refresh");
        Button loadBarGraph = new Button("Load Bar graph");
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(refresh, loadBarGraph);

        refresh.setOnAction(e->{
            createInventoryChart();
        });
        loadBarGraph.setOnAction(event -> {
            createOrderGraph();
        });

        createInventoryChart();
//        createInventoryChart();
        root.setBottom(buttonHBox);
        this.setContent(root);
    }

    public void createInventoryChart(){
        ArrayList<PieChart.Data> data = new ArrayList<>();
        for (Inventory inventory : inventories){
            int id = inventory.getProductId();
            data.add(new PieChart.Data(productTable.getProduct(id).getName(), inventory.getQuantity()));

        }
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
        chart.setData(chartData);
    }

    public void createOrderGraph(){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Series");

        for (Inventory inventory : inventories){
            int id = inventory.getProductId();
            series.getData().add(new XYChart.Data<>(productTable.getProduct(id).getName(), inventory.getQuantity()));
        }
        barChart.getData().add(series);
    }

    public static ChartTab getInstance() {
        if (instance == null) {
            instance = new ChartTab();
        }
        return instance;
    }
}
