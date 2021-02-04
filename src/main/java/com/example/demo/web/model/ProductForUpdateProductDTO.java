package com.example.demo.web.model;

import com.example.demo.Model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductForUpdateProductDTO {

    private String category;

    private String hash;

    @NotNull(message = "Цена не должена быть пустой")
    @Min(value = 1, message = "Цена должна быть не меньше 1")
    @Max(value = 100000, message = "Цена должна быть не больше 100000")
    private Integer price;

    @NotNull(message = "Имя товара не должено быть пустым")
    @Size(min = 2, max = 50, message = "Имя товара не должено превышать 50 символов и не должено быть меньше 2 символов")
    private String title;

    @NotNull(message = "Описание не должно быть пустым")
    @Size(min = 5, message = "Описание не должно быть меньше 5 символов")
    private String descriptions;

    @NotNull(message = "Путь до картинки не может быть null")
    @NotEmpty(message = "Путь до картинки не может быть пустым")
    private String mainImage;

    private List<ImageDTO> imagePaths;

    public void setImagePathsByImageDTO(Set<Image> listImage) {
        this.imagePaths = new ArrayList<>();
        for (Image image : listImage){
            this.imagePaths.add(new ImageDTO(image,false));
        }
    }
}
