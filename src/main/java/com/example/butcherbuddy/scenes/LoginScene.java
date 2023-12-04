package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.LoginPane;
import javafx.scene.Scene;

public class LoginScene extends Scene {
    public LoginScene(){super (new LoginPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);}
}
