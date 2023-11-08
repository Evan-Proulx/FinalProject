package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.CreditsPane;
import javafx.scene.Scene;

public class CreditsScene extends Scene {
    public CreditsScene(){
        super(new CreditsPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
    }
}
