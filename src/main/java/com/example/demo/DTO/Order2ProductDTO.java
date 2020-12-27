package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order2ProductDTO {

    private Integer productId;

    private Integer count;

    private String size;

    private String imageName;

    private String category;

    private Integer price;

    private String title;

    /*"{" +
            "\"productId\":" + productId +
            ",\"imageName\":\"" + imageName + '\"' +
            ",\"price\":" + price +
            ",\"title\":\"" + title + '\"' +
            ",\"count\":" + count +
            ",\"size\":\"" + size + '\"' +
            '}';*/

    @Override
    public String toString() {
        return "{" +
                "\"productId\":" + productId +
                ",\"count\":" + count +
                ",\"size\":\"" + size + '\"' +
                ",\"imageName='" + imageName + '\"' +
                ",\"category\":\"" + category + '\"' +
                ",\"price\":" + price +
                ",\"title\":\"" + title + '\"' +
                '}';
    }

}
