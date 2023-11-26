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
        //System.out.println(query);
        try{
            Statement getLogin = db.getConnection().createStatement();
            ResultSet data = getLogin.executeQuery(query);
            if (data.next()){
                Login login = new Login(
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
    public int createLogin(Login login) {
        String query = "INSERT INTO " + DBConst.TABLE_LOGINS + " (`id`, `username`, `password`) VALUES (NULL, '" + login.getUsername() + "', '" + login.getPassword() + "');";

        //executes the query then creates a resultset of keys(Auto-Incremented ids)
        //If there are more than one row in the table we get and return the value of the first column(id)
        try{
            Statement createNewLogin = db.getConnection().createStatement();
            createNewLogin.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet returnedKeys = createNewLogin.getGeneratedKeys();
            if (returnedKeys.next()){
                int generatedId = returnedKeys.getInt(1);

                System.out.println("Inserted Record with id: " + generatedId);
                return generatedId;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void updateLogin(Login login) {

    }

    @Override
    public void deleteLogin(Login login) {

    }


}
