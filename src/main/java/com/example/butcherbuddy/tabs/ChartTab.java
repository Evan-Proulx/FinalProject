package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.pojo.Inventory;
import com.example.butcherbuddy.tables.InventoryTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

public class ChartTab extends Tab {

    private static ChartTab instance;

    private PieChart chart;


    private ChartTab(){
        this.setText("Stats");
        BorderPane root = new BorderPane();
        chart = new PieChart();
        chart.setTitle("All inventory Products");
        chart.setLabelsVisible(true);
        root.setCenter(chart);
        Button refresh = new Button("Refresh");
        refresh.setOnAction(e->{
            createChart();
        });

        createChart();
        root.setBottom(refresh);
        this.setContent(root);
    }

    public void createChart(){
        InventoryTable inventoryTable = InventoryTable.getInstance();

        ArrayList<Inventory> inventories = inventoryTable.getAllInventories();

        ArrayList<PieChart.Data> data = new ArrayList<>();
        for (Inventory inventory : inventories){
            data.add(new PieChart.Data(inventory.getProductIdasString(), inventory.getQuantity()));

            }
        ObservableList<PieChart.Data> chartData
                = FXCollections.observableArrayList(data);
        chart.setData(chartData);
    }


    public static ChartTab getInstance() {
        if (instance == null) {
            instance = new ChartTab();
        }
        return instance;
    }
}
