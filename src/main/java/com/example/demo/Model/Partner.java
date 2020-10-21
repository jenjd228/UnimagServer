package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "image_name")
    private String imageName; //Название картинки с логотипом партнера

    private String title; //Название партнера

    private String description; //Описание скидки партнера

    private BigDecimal price; //Цена подписки

}
