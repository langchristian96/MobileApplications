package com.example.langchristian96.androidshopping.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.sql.Date;


/**
 * Created by langchristian96 on 11/9/2017.
 */

@Entity(tableName = "shopping_list")
public class ShoppingList {
    @PrimaryKey(autoGenerate = true)
    private Long id;
    private String slName;
    private String slDescription;
    private int buyHour;
    private int buyMinute;

    public int getBuyHour() {
        return buyHour;
    }

    public void setBuyHour(int buyHour) {
        this.buyHour = buyHour;
    }

    public int getBuyMinute() {
        return buyMinute;
    }

    public void setBuyMinute(int buyMinute) {
        this.buyMinute = buyMinute;
    }

    @Ignore
    public ShoppingList(String name, String description) {
        this.slName = name;
        this.slDescription = description;
    }

    public ShoppingList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlName() {
        return slName;
    }

    public void setSlName(String slName) {
        this.slName = slName;
    }

    public String getSlDescription() {
        return slDescription;
    }

    public void setSlDescription(String slDescription) {
        this.slDescription = slDescription;
    }
}
