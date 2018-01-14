package com.example.langchristian96.androidshopping.repository;

import android.arch.persistence.room.Room;
import android.util.Log;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;
import com.example.langchristian96.androidshopping.utils.Globals;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by langchristian96 on 11/9/2017.
 */

public class ShoppingListRepository {
    private static final String TAG = "ShoppingListRepository";
    private List<ShoppingList> repo;
    private List<ShoppingListProduct> shoppingListProducts;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();
    private DatabaseReference shoppingListsDatabase;
    private DatabaseReference shoppingListsProductsDatabase;

    public ShoppingListRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        Globals.mFirebaseInstance.getReference("admin").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String adminUser = dataSnapshot.getValue(String.class);
                Globals.administrator = adminUser;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        this.appDatabase = appDatabase;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                repo.addAll(appDatabase.shoppingListDao().getAll());
            }
        });
        shoppingListsDatabase = Globals.mFirebaseInstance.getReference("shoppinglists");
        shoppingListsProductsDatabase = Globals.mFirebaseInstance.getReference("shoppinglistsproducts");

        shoppingListsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<ShoppingList>> genericTypeIndicator = new GenericTypeIndicator<List<ShoppingList>>() {};
                repo = (List<ShoppingList>) dataSnapshot.getValue(genericTypeIndicator);
                if(Globals.shoppingListsAdapter!= null) {
                    Globals.shoppingListsAdapter.setDataset(repo);
                    Globals.shoppingListsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "Failed to read shopping lists value", databaseError.toException());
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                shoppingListProducts = appDatabase.shoppingListProductDao().getAll();
                shoppingListsProductsDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<ShoppingListProduct>> genericTypeIndicator = new GenericTypeIndicator<List<ShoppingListProduct>>() {};
                        List<ShoppingListProduct> shoppingListProducts = (List<ShoppingListProduct>) dataSnapshot.getValue(genericTypeIndicator);
                        for(final ShoppingListProduct slp: shoppingListProducts) {
                            if(!shoppingListProducts.contains(slp)) {
                                executor.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        appDatabase.shoppingListProductDao().insertAll(slp);
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, "Failed to read shopping lists - products value", databaseError.toException());
                    }
                });
            }
        });
    }

    public void add(final ShoppingList e) {
        repo.add(e);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.shoppingListDao().insertAll(e);
            }
        });
        shoppingListsDatabase.setValue(repo);
    }

    public void update(final ShoppingList e) {
        ShoppingList initialShoppingList = null;
        for (ShoppingList sl : repo) {
            if (sl.getId() == e.getId()) {
                initialShoppingList = sl;
                break;
            }
        }
        initialShoppingList.setSlDescription(e.getSlDescription());
        initialShoppingList.setSlName(e.getSlName());

        final ShoppingList toUpdateList = initialShoppingList;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.shoppingListDao().update(toUpdateList);
            }
        });
        shoppingListsDatabase.setValue(repo);
    }

    public void delete(final ShoppingList shoppingList) {

        for (ShoppingList sl : repo) {
            if (sl.getId() == shoppingList.getId()) {
                repo.remove(sl);
                final ShoppingList toDelete = sl;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.shoppingListDao().delete(toDelete);
                    }
                });
                break;
            }
        }
        shoppingListsDatabase.setValue(repo);
    }

    public List<ShoppingList> getAll() {
        return repo;
    }

    public List<Product> getProducts(final Long shoppingListId) {
        final List<Product> products = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                products.addAll(appDatabase.shoppingListProductDao().getProducts(shoppingListId));
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return products;
    }


    public List<ShoppingListProduct> getShoppingListProducts() {
        final List<ShoppingListProduct> products = new ArrayList<>();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                products.addAll(appDatabase.shoppingListProductDao().getAll());
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean addProductToList(final Long shoppingListId, final Long productId) {
        final ShoppingListProduct shoppingListProduct = new ShoppingListProduct();
        shoppingListProduct.setProductId(productId);
        shoppingListProduct.setShoppingListId(shoppingListId);
        List<Product> crtProducts = getProducts(shoppingListId);
        for (Product prod : crtProducts) {
            if (prod.getProductId() == productId) {
                return false;
            }
        }

        executor.execute(new Runnable() {
            @Override
            public void run() {
                appDatabase.shoppingListProductDao().insertAll(shoppingListProduct);
            }
        });
        shoppingListProducts.add(shoppingListProduct);
        shoppingListsProductsDatabase.setValue(shoppingListProducts);
        return true;
    }
}
