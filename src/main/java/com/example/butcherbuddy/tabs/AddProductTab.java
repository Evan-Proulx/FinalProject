package com.example.butcherbuddy.tabs;

import javafx.scene.control.Tab;

public class AddProductTab extends Tab {
    private static AddProductTab instance;

    private AddProductTab(){

    }


    public static AddProductTab getInstance(){
        if (instance == null){
            instance = new AddProductTab();
        }return instance;
    }
}
