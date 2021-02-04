package com.example.demo.managers;

import com.example.demo.web.model.Category;

import java.util.HashMap;

public final class MyHashMapManager {

    private static MyHashMapManager instance;

    private MyHashMapManager() {

    }

    public static MyHashMapManager getInstance() {
        if (instance == null) {
            instance = new MyHashMapManager();
        }
        return instance;
    }

    public HashMap<Integer, Category> cloneHashMapAndSetSelected(HashMap<Integer, Category> oldHashMap, String selectedCategory) {
        HashMap<Integer, Category> newMap = new HashMap<>();
        for (Integer key : oldHashMap.keySet()) {
            Category category = oldHashMap.get(key);
            if (category.getNameEn().equals(selectedCategory)) {
                newMap.put(key, new Category(category.getNameRu(), category.getNameEn(), true));
            } else {
                newMap.put(key, new Category(category.getNameRu(), category.getNameEn(), category.getIsSelected()));
            }
        }
        return newMap;
    }
}
