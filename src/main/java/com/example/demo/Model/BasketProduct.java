package com.example.demo.Model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "basket_product")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_hash")
    private String productHash;

    @Column(name = "user_id")
    private Integer userId;

    @NotNull
    @Column(name = "product_id")
    private Integer productId;

    private Integer count;

    @Column(name="color")
    private String color;

    @Column(name="size")
    private String size;

    @OneToOne()
    @JoinColumn(name = "product_id", insertable = false,updatable = false)
    private Catalog catalogProduct;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasketProduct that = (BasketProduct) o;
        return userId.equals(that.userId) &&
                productId.equals(that.productId) &&
                Objects.equals(count, that.count) &&
                Objects.equals(color, that.color) &&
                Objects.equals(size, that.size);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, count, color, size);
    }
}
