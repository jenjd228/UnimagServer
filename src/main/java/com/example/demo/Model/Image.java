package com.example.demo.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Images")
public class Image implements Serializable {

    @EmbeddedId
    private ImageKey key;

    @Embeddable
    @Data
    public static class ImageKey implements Serializable {

        @Column(name = "product_id")
        @com.sun.istack.NotNull
        private Integer productId;

        private String imageName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(key, image.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
