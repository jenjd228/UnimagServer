package com.example.demo.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForAddProductDTO {

    private String category;

    @NotNull(message = "Цена не должена быть пустой")
    @Min(value = 1, message = "Цена должна быть не меньше 1")
    @Max(value = 100000, message = "Цена должна быть не больше 100000")
    private Integer price;

    @NotNull(message = "Имя товара не должено быть пустым")
    @Size(min = 2, max = 30, message = "Имя товара не должено превышать 30 символов и не должено быть меньше 2 символов")
    private String title;

    @NotNull(message = "Описание не должно быть пустым")
    @Size(min = 5, message = "Описание не должно быть меньше 5 символов")
    private String descriptions;

    //private MultipartFile file;
    @NotNull(message = "Путь до картинки не может быть null")
    @NotEmpty(message = "Путь до картинки не может быть пустым")
    private String mainImage;
}
