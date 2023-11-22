package com.example.butcherbuddy.panes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.pojo.OrderItem;
import com.example.butcherbuddy.pojo.Orders;
import com.example.butcherbuddy.pojo.Product;
import com.example.butcherbuddy.tables.*;
import com.example.butcherbuddy.tabs.FormTab;
import com.example.butcherbuddy.tabs.InventoryTab;
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

    public OrderFormPane() {
        TabPane tabPane = new TabPane();

        InventoryTab inventoryTab = InventoryTab.getInstance();
        FormTab formTab = FormTab.getInstance();
        inventoryTab.setClosable(false);
        formTab.setClosable(false);

        tabPane.getTabs().addAll(formTab, inventoryTab);

        this.setCenter(tabPane);

    }
}