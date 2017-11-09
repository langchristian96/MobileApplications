package com.example.langchristian96.androidshopping.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by langchristian96 on 11/9/2017.
 */

public class Repository<T> {
    private List<T> repo;

    public Repository() {
        this.repo = new ArrayList<>();
    }

    public void add(T e) {
        repo.add(e);
    }

    public List<T> getAll() {
        return repo;
    }
}
