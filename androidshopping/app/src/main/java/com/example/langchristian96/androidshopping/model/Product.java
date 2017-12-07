package com.example.langchristian96.androidshopping.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by langchristian96 on 12/6/2017.
 */

@Entity(tableName = "product")
public class Product {
    @PrimaryKey(autoGenerate = true)
    private Long productId;
    private String pname;
    private String pdescription;

    public Product() {
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPdescription() {
        return pdescription;
    }

    public void setPdescription(String pdescription) {
        this.pdescription = pdescription;
    }

    public String getPname() {

        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    @Override
    public String toString() {
        return pname;
    }
}
