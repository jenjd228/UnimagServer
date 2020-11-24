package com.example.demo.DTO;

import com.example.demo.Model.BasketProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductDTO {

    private Integer productId;

    private String imageName;

    private String category;

    private Integer price;

    private String title;

    private String descriptions;

    private Integer count;

    private String color;

    private Integer size; //Размер одежды

    public void setCount(BasketProduct basketProduct){
        this.count = basketProduct.getCount();
    }
}
