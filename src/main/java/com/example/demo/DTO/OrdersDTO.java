package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {

    private Integer id;

    private Integer userId;

    private Integer productId;

    private Date dateOfOrder;

}

