package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.pojo.Inventory;
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

public class ChartTab extends Tab {
    private static ChartTab instance;
    private PieChart chart;
    InventoryTable inventoryTable = InventoryTable.getInstance();
    ProductTable productTable = ProductTable.getInstance();
    ArrayList<Inventory> inventories = inventoryTable.getAllInventories();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);


    private ChartTab() {
        BorderPane root = new BorderPane();

        xAxis.setLabel("Product");
        yAxis.setLabel("Amount");

        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();
        xAxis.setTickLabelRotation(45);
        barChart.setCategoryGap(20);

        barChart.setTitle("Simple Bar Chart Example");
        barChart.setVisible(true);

        chart = new PieChart();
        chart.setTitle("All inventory Products");
        chart.setLabelsVisible(true);

        root.setCenter(barChart);
        createOrderGraph();

        Button refresh = new Button("Refresh");
        ToggleButton toggleChartBtn = new ToggleButton("Toggle Chart Style");
        toggleChartBtn.getStyleClass().add("toggle-switch");
        HBox buttonHBox = new HBox();
        buttonHBox.getChildren().addAll(refresh, toggleChartBtn);

        //Recalls the currently selected chart
        refresh.setOnAction(e -> {
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
            data.add(new PieChart.Data(productTable.getProduct(id).getName(), inventory.getQuantity()));
        }
        ObservableList<PieChart.Data> chartData = FXCollections.observableArrayList(data);
        chart.setData(chartData);
    }

    //Takes all records from inventory table and displays product name and quantity in bar chart
    public void createOrderGraph() {
        barChart.getData().clear();

        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (Inventory inventory : inventories) {
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
