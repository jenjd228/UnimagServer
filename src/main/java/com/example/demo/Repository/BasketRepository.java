package com.example.demo.Repository;

import com.example.demo.Model.BasketProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketRepository extends CrudRepository<BasketProduct, Long> {

    BasketProduct findById(Integer id);

    void deleteById(Integer id);
}
