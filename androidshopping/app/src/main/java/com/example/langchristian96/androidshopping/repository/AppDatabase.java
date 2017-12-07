package com.example.langchristian96.androidshopping.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;

/**
 * Created by langchristian96 on 12/6/2017.
 */

@Database(entities = {ShoppingList.class, Product.class, ShoppingListProduct.class}, version = 6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ShoppingListDao shoppingListDao();
    public abstract ProductDao productDao();
    public abstract ShoppingListProductDao shoppingListProductDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "list-database")
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}