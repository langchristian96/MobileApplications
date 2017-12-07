package com.example.langchristian96.androidshopping.repository;

import android.arch.persistence.room.Room;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;
import com.example.langchristian96.androidshopping.model.ShoppingListProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by langchristian96 on 11/9/2017.
 */

public class ShoppingListRepository {
    private List<ShoppingList> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

    public ShoppingListRepository(final AppDatabase appDatabase) {
        this.repo = new ArrayList<>();
        this.appDatabase = appDatabase;
        executor.execute(new Runnable() {

            @Override
            public void run() {
                repo.addAll(appDatabase.shoppingListDao().getAll());
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
        return true;
    }
}
