package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

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

    private Date date;

    private Integer count;
}
