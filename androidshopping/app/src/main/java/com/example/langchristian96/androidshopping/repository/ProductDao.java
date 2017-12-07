package com.example.langchristian96.androidshopping.repository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;

import java.util.List;

/**
 * Created by langchristian96 on 12/6/2017.
 */
@Dao
public interface ProductDao {

    @Query("SELECT * FROM product")
    List<Product> getAll();


    @Query("SELECT * FROM product where productId = :id")
    Product getProductById(Long id);

    @Insert
    void insertAll(Product... products);


    @Delete
    void delete(Product product);


    @Update
    void update(Product product);
}
