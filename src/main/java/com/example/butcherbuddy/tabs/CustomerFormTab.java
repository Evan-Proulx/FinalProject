package com.example.butcherbuddy.tabs;

import com.example.butcherbuddy.OrderLogic;
import javafx.scene.control.Tab;

public class CustomerFormTab extends Tab {

    OrderLogic orderLogic = new OrderLogic();

    private static CustomerFormTab instance;

    private CustomerFormTab() {



        this.setText("Customer Order Form");
    }


    public static CustomerFormTab getInstance() {
        if (instance == null) {
            instance = new CustomerFormTab();
        }
        return instance;
    }
}
