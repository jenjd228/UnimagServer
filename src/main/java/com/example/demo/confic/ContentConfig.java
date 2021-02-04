package com.example.demo.confic;

import com.example.demo.web.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ContentConfig {

    @Bean
    public HashMap<Integer, Category> allCategoriesMap(){
        HashMap<Integer, Category> hashMap = new HashMap<>();
        hashMap.put(1,new Category("Одежда","Clothes",false));
        hashMap.put(2,new Category("Сувениры","Souvenirs",false));
        return hashMap;
    }

}
