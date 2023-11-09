package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.CategoryDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Category;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryTable implements CategoryDAO{

    Database db = Database.getInstance();

    ArrayList<Category> categories;

    @Override
    public ArrayList<Category> getAllCategories() {
        String query = "SELECT * FROM " + DBConst.TABLE_CATEGORY;

        categories = new ArrayList<>();
        try{
            Statement getCategory = db.getConnection().createStatement();
            ResultSet data = getCategory.executeQuery(query);

            while(data.next()){
                categories.add(new Category(
                        data.getInt(DBConst.CATEGORY_COLUMN_ID),
                        data.getString(DBConst.CATEGORY_COLUMN_NAME)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return categories;
    }

    @Override
    public Category getCategory(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_CATEGORY + " WHERE " + DBConst.CATEGORY_COLUMN_ID  + " = " + id;

        try{
            Statement getCategory = db.getConnection().createStatement();
            ResultSet data = getCategory.executeQuery(query);
            if (data.next()){
                Category category = new Category(
                        data.getInt(DBConst.CATEGORY_COLUMN_ID),
                        data.getString(DBConst.CATEGORY_COLUMN_NAME)
                );
                return category;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void createCategory(Category category) {

    }

    @Override
    public void updateCategory(Category category) {

    }

    @Override
    public void deleteCategory(Category category) {

    }


}
