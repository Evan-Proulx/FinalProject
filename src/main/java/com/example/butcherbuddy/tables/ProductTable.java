package com.example.butcherbuddy.tables;

import com.example.butcherbuddy.dao.ProductDAO;
import com.example.butcherbuddy.database.DBConst;
import com.example.butcherbuddy.database.Database;
import com.example.butcherbuddy.pojo.Product;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductTable implements ProductDAO {
    Database db = Database.getInstance();

    ArrayList<Product> products;
    @Override
    public ArrayList<Product> getAllProducts() {
        String query = "SELECT * FROM " + DBConst.TABLE_PRODUCT;

        products = new ArrayList<>();
        try{
            Statement getProducts = db.getConnection().createStatement();
            ResultSet data = getProducts.executeQuery(query);

            while(data.next()){
                products.add(new Product(
                        data.getInt(DBConst.PRODUCT_COLUMN_ID),
                        data.getString(DBConst.PRODUCT_COLUMN_NAME),
                        data.getDouble(DBConst.PRODUCT_COLUMN_PRICE),
                        data.getInt(DBConst.PRODUCT_COLUMN_CATEGORY)
                ));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product getProduct(int id) {
        String query = "SELECT * FROM " + DBConst.TABLE_PRODUCT + " WHERE " + DBConst.PRODUCT_COLUMN_ID  + " = " + id;

        try{
            Statement getProduct = db.getConnection().createStatement();
            ResultSet data = getProduct.executeQuery(query);
            if (data.next()){
                Product product = new Product(
                        data.getInt(DBConst.PRODUCT_COLUMN_ID),
                        data.getString(DBConst.PRODUCT_COLUMN_NAME),
                        data.getDouble(DBConst.PRODUCT_COLUMN_PRICE),
                        data.getInt(DBConst.PRODUCT_COLUMN_CATEGORY)
                );
                return product;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void createProduct(Product product) {

    }

    @Override
    public void updateProduct(Product product) {

    }

    @Override
    public void deleteProduct(Product product) {

    }
}
