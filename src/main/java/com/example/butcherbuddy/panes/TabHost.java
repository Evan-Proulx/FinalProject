package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.tabs.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class TabHost extends BorderPane {

    public TabHost() {
        TabPane tabPane = new TabPane();
        this.getStylesheets().add("com.example.butcherbuddy/style.css");

        InventoryTab inventoryTab = InventoryTab.getInstance();
        FormTab formTab = FormTab.getInstance();
        CustomerFormTab customerFormTab = CustomerFormTab.getInstance();
        AddProductTab addProductTab = AddProductTab.getInstance();
        ChartTab chartTab = ChartTab.getInstance();

        inventoryTab.setClosable(false);
        formTab.setClosable(false);
        customerFormTab.setClosable(false);
        addProductTab.setClosable(false);
        chartTab.setClosable(false);


        tabPane.getTabs().addAll(formTab,customerFormTab, inventoryTab, addProductTab, chartTab);

        //Refreshes the inventory table when the inventory tab is opened
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null){
                inventoryTab.refreshTable();
            }
        });

        this.setCenter(tabPane);

    }
}