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
public interface ShoppingListProductDao {

    @Query("select * from product " +
            " inner join shopping_list_product on product.productId = shopping_list_product.productId " +
            " where shopping_list_product.shoppingListId = :slId")
    List<Product> getProducts(Long slId);

    @Query("select * from shopping_list_product")
    List<ShoppingListProduct> getAll();

    @Insert
    void insertAll(ShoppingListProduct... lists);


    @Delete
    void delete(ShoppingListProduct shoppingListProduct);


    @Update
    void update(ShoppingListProduct shoppingListProduct);
}
