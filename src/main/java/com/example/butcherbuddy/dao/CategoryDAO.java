package com.example.butcherbuddy.dao;

import com.example.butcherbuddy.pojo.Category;

import java.util.ArrayList;

public interface CategoryDAO {
    public ArrayList<Category> getAllCategories();
    public Category getCategory(int id);
    public void createCategory(Category category);
    public void updateCategory(Category category);
    public void deleteCategory(Category category);
}
