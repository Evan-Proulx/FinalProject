package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.TabHost;
import javafx.scene.Scene;

public class TabHostScene extends Scene {
    public TabHostScene(){super (new TabHost(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);}
}
