package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    String imageName; //Имя картинки (без расширения)

    String title; //Название товара

    String description; //Описание товара

    Integer price;
}
