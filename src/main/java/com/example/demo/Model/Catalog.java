package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String imageName;

    private String category;

    private Integer price;

    private String title;

    private String descriptions;

    private LocalDateTime date;

    @OneToMany()
    @JoinColumn(name = "product_id", referencedColumnName = "id",insertable=false, updatable=false)
    private List<Image> listImage;

}
