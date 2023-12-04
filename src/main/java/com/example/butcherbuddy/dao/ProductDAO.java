package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Product;

import java.util.ArrayList;

public interface ProductDAO {
    public ArrayList<Product> getAllProducts();
    public Product getProduct(int id);

    Product getProductName(String name);

    public void createProduct(Product product);
    public void updateProduct(Product product);
    public void deleteProduct(Product product);
}
