package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.database.Database;
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
        ManageProductTab manageProductTab = ManageProductTab.getInstance();
        ChartTab chartTab = ChartTab.getInstance();

        inventoryTab.setClosable(false);
        formTab.setClosable(false);
        customerFormTab.setClosable(false);
        manageProductTab.setClosable(false);
        chartTab.setClosable(false);

        manageProductTab.getStyleClass().add("tab");
        formTab.getStyleClass().add("tab");
        chartTab.getStyleClass().add("tab");
        customerFormTab.getStyleClass().add("tab");


        tabPane.getTabs().addAll(manageProductTab, formTab,customerFormTab, chartTab);

        //Refreshes the inventory table when the inventory tab is opened
        tabPane.getSelectionModel().selectedIndexProperty().addListener((observable, oldTab, newTab) -> {
            if (oldTab != null){
                formTab.refreshTable();
                customerFormTab.refreshTable();
            }
        }); //may cause lag on lower end pcs

        this.setCenter(tabPane);

    }
}