package com.example.langchristian96.androidshopping.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;

import java.util.List;

/**
 * Created by langchristian96 on 12/6/2017.
 */

@Dao
public interface ShoppingListDao {

    @Query("SELECT * FROM shopping_list")
    List<ShoppingList> getAll();

    @Insert
    void insertAll(ShoppingList... lists);


    @Delete
    void delete(ShoppingList shoppingList);


    @Update
    void update(ShoppingList shoppingList);
}
