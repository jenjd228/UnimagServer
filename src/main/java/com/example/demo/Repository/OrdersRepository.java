package com.example.demo.Repository;

import com.example.demo.Model.Orders;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends CrudRepository<Orders, Integer> {
    Orders findByUserId(Integer id);
}
