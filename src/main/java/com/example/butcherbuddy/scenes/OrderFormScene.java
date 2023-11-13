package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.LoginPane;
import com.example.butcherbuddy.panes.OrderFormPane;
import javafx.scene.Scene;

public class OrderFormScene extends Scene {
    public OrderFormScene(){super (new OrderFormPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);}
}
