package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.LoginsDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Login;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoginsTable implements LoginsDAO {

    Database db = Database.getInstance();

    ArrayList<Login> logins;

    @Override
    public ArrayList<Login> getAllLogins() {
        String query = "SELECT * FROM " + DBConst.TABLE_LOGINS;

        logins = new ArrayList<>();
        try{
            Statement getLogin = db.getConnection().createStatement();
            ResultSet data = getLogin.executeQuery(query);

            while(data.next()){
                logins.add(new Login(
                        data.getInt(DBConst.LOGINS_COLUMN_ID),
                        data.getString(DBConst.LOGINS_COLUMN_NAME),
                        data.getString(DBConst.LOGINS_COLUMN_PASSWORD)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return logins;
    }

    @Override
    public Login getLogin(String username) {
        String query = "SELECT * FROM " + DBConst.TABLE_LOGINS + " WHERE " + DBConst.LOGINS_COLUMN_NAME  + " = '" + username + "'";
        System.out.println(query);
        try{
            Statement getLogin = db.getConnection().createStatement();
            ResultSet data = getLogin.executeQuery(query);
            if (data.next()){
                Login login = new Login(
                        data.getInt(DBConst.LOGINS_COLUMN_ID),
                        data.getString(DBConst.LOGINS_COLUMN_NAME),
                        data.getString(DBConst.LOGINS_COLUMN_PASSWORD)
                );
                return login;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createLogin(Login login) {

    }

    @Override
    public void updateLogin(Login login) {

    }

    @Override
    public void deleteLogin(Login login) {

    }


}
