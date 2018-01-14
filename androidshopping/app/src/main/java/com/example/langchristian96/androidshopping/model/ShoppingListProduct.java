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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingListProduct that = (ShoppingListProduct) o;

        if (shoppingListId != null ? !shoppingListId.equals(that.shoppingListId) : that.shoppingListId != null)
            return false;
        return productId != null ? productId.equals(that.productId) : that.productId == null;

    }

    @Override
    public int hashCode() {
        int result = shoppingListId != null ? shoppingListId.hashCode() : 0;
        result = 31 * result + (productId != null ? productId.hashCode() : 0);
        return result;
    }
}
