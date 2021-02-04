package com.example.demo.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private String nameRu;

    private String nameEn;

    private Boolean isSelected;

}
