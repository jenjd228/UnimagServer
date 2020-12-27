package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayDTO {

    private Integer productId;

    private String imageName;

    private Integer price;

    private String title;

    private Integer count;

    private String size;
}
