package com.example.demo.Repository;

import com.example.demo.Model.BasketProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasketRepository extends CrudRepository<BasketProduct, Long> {
    List<BasketProduct> findByUserId(Integer id);
    BasketProduct findByProductId(Integer id);
    void deleteById(Integer id);

}
