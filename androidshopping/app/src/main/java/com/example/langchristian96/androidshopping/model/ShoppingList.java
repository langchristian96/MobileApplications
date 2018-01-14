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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingList that = (ShoppingList) o;

        if (buyHour != that.buyHour) return false;
        if (buyMinute != that.buyMinute) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (slName != null ? !slName.equals(that.slName) : that.slName != null) return false;
        return slDescription != null ? slDescription.equals(that.slDescription) : that.slDescription == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (slName != null ? slName.hashCode() : 0);
        result = 31 * result + (slDescription != null ? slDescription.hashCode() : 0);
        result = 31 * result + buyHour;
        result = 31 * result + buyMinute;
        return result;
    }
}
