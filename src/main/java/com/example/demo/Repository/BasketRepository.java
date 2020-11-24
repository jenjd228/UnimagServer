package com.example.demo.Repository;

import com.example.demo.Model.BasketProduct;
import org.hibernate.annotations.Cascade;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BasketRepository extends CrudRepository<BasketProduct, Long> {

    @Query("select o from BasketProduct o WHERE o.userId = ?1 AND o.productId = ?2")
    List<BasketProduct> findByIdInUserBasket(Integer userId, Integer productId);

    void deleteById(Integer id);
}
