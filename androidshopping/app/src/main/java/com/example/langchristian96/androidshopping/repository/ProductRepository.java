package com.example.langchristian96.androidshopping.repository;

import android.util.Log;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.utils.Globals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by langchristian96 on 12/6/2017.
 */

public class ProductRepository {

    private static final String TAG = "ProductRepository";
    private List<Product> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();
    private DatabaseReference productsDatabase;

    public ProductRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                repo.addAll(appDatabase.productDao().getAll());
//                Product p1 = new Product();
//                p1.setPdescription("product1d");
//                p1.setPname("product1");
//                Product p2 = new Product();
//                p2.setPdescription("product2d");
//                p2.setPname("product2");
//                Product p3 = new Product();
//                p3.setPdescription("product3d");
//                p3.setPname("product3");
//                add(p1);
//                add(p2);
//                add(p3);


                GenericTypeIndicator<List<Product>> genericTypeIndicator = new GenericTypeIndicator<List<Product>>() {};
                productsDatabase = Globals.mFirebaseInstance.getReference("products");
                productsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<Product>> genericTypeIndicator = new GenericTypeIndicator<List<Product>>() {};

                        repo = (List<Product>) dataSnapshot.getValue(genericTypeIndicator);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Failed to read products value", databaseError.toException());
                    }
                });
            }
        });

    }


    public void add(final Product e) {
        repo.add(e);
        productsDatabase.setValue(repo);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.productDao().insertAll(e);
            }
        });
    }

    public void update(final Product e) {
        Product initialShoppingList = null;
        for(Product sl : repo) {
            if(sl.getProductId() == e.getProductId()) {
                initialShoppingList = sl;
                break;
            }
        }
        initialShoppingList.setPdescription(e.getPdescription());
        initialShoppingList.setPname(e.getPname());

        final Product toUpdateList = initialShoppingList;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.productDao().update(toUpdateList);
            }
        });

        productsDatabase.setValue(repo);
    }

    public void delete(final Product product) {

        for(Product sl : repo) {
            if(sl.getProductId() == product.getProductId()) {
                repo.remove(sl);
                final Product toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.productDao().delete(toDelete);
                    }
                });
                break;
            }
        }

        productsDatabase.setValue(repo);
    }

    public List<Product> getAll() {
        return repo;
    }

    public Product getProductById(final Long id) {
        for(Product p:repo) {
            if(p.getProductId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}
