package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String imageName; //Имя картинки (без расширения)

    private String title; //Название товара

    private String description; //Описание товара

    private Integer price;
}
