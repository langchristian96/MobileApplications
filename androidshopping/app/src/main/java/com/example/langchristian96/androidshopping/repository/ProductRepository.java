package com.example.langchristian96.androidshopping.repository;

import com.example.langchristian96.androidshopping.model.Product;
import com.example.langchristian96.androidshopping.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

/**
 * Created by langchristian96 on 12/6/2017.
 */

public class ProductRepository {

    private List<Product> repo;
    private final AppDatabase appDatabase;
    private Executor executor = Executors.newSingleThreadExecutor();

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
            }
        });
    }

    public void add(final Product e) {
        repo.add(e);

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
