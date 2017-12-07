package com.example.langchristian96.androidshopping.model;

import android.arch.persistence.room.Entity;

/**
 * Created by langchristian96 on 12/6/2017.
 */
@Entity(primaryKeys = {"shoppingListId", "productId"}, tableName = "shopping_list_product")
public class ShoppingListProduct {
    private Long shoppingListId;
    private Long productId;

    public Long getShoppingListId() {
        return shoppingListId;
    }

    public void setShoppingListId(Long shoppingListId) {
        this.shoppingListId = shoppingListId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
