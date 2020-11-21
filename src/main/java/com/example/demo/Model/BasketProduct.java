package com.example.demo.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "basket_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    private Integer count;

    @OneToOne()
    @JoinColumn(name = "product_id", insertable = false,updatable = false)
    private Catalog catalogProduct;
}
