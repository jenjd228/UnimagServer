package com.example.demo.Repository;

import com.example.demo.DTO.Order2ProductInterface;
import com.example.demo.Model.Order2Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Order2productRepository extends CrudRepository<Order2Product,Long> {

    @Query(value = "SELECT order_2_product.product_id as productId, order_2_product.count, order_2_product.size, catalog.category, catalog.image_name as imageName, catalog.title,catalog.price" +
            " FROM order_2_product" +
            " join orders ON orders.order_id = order_2_product.order_id" +
            " join catalog ON catalog.id = order_2_product.product_id" +
            " where orders.user_id = ?1 and orders.order_id = ?2",nativeQuery = true)
    List<Order2ProductInterface> findProductBySecureKodAndOrderId(Integer userId, Integer orderId);

}
