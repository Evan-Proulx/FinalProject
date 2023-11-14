package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Login;

import java.util.ArrayList;

public interface LoginsDAO {
    public ArrayList<Login> getAllLogins();
    public Login getLogin(String username);
    public void createLogin(Login Login);
    public void updateLogin(Login Login);
    public void deleteLogin(Login Login);
}
