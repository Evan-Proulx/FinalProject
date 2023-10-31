package com.example.butcherbuddy;

import com.example.projectclick.Const;
import com.example.projectclick.panes.MenuPane;
import javafx.scene.Scene;

public class MenuScene extends Scene {
    public MenuScene(){
        super(new MenuPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
    }
}
