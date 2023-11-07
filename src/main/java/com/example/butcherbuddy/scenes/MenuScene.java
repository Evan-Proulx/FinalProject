package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.MenuPane;
import javafx.scene.Scene;

public class MenuScene extends Scene {
    public MenuScene(){
        super(new MenuPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
    }
}
