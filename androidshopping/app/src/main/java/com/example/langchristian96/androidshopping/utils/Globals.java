package com.example.langchristian96.androidshopping.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.langchristian96.androidshopping.repository.ProductRepository;
import com.example.langchristian96.androidshopping.repository.ShoppingListRepository;
import com.example.langchristian96.androidshopping.view.ShoppingListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Date;

/**
 * Created by langchristian96 on 11/9/2017.
 */

public class Globals {
    public static ShoppingListRepository shoppingListRepository = null;
    public static ProductRepository productRepository = null;
    public static String user = null;
    public static String administrator= "";
    public static ShoppingListAdapter shoppingListsAdapter;
    public static AppCompatActivity currentActivity;
    public static int buyHour;
    public static int buyMinute;
    public final static FirebaseDatabase mFirebaseInstance = FirebaseDatabase.getInstance();
    static {
        mFirebaseInstance.setPersistenceEnabled(true);
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
    }
}
