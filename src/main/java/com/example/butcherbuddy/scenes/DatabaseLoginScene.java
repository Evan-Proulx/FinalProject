package com.example.butcherbuddy.scenes;

import com.example.butcherbuddy.Const;
import com.example.butcherbuddy.panes.DatabaseLoginPane;
import com.example.butcherbuddy.panes.LoginPane;
import javafx.scene.Scene;

public class DatabaseLoginScene extends Scene {
    public DatabaseLoginScene(){super (new DatabaseLoginPane(), Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);}
}
