package com.example.demo.Service;

import com.example.demo.Bonus;
import com.example.demo.Model.Order2Product;
import com.example.demo.Model.Orders;
import com.example.demo.Model.User;
import com.example.demo.Repository.Order2productRepository;
import com.example.demo.Repository.OrdersRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private OrdersRepository ordersRepository;

    private UserRepository userRepository;

    private Order2productRepository order2ProductRepository;

    OrderService(Order2productRepository order2ProductRepository, OrdersRepository ordersRepository , UserRepository userRepository){
        this.ordersRepository = ordersRepository;
        this.userRepository = userRepository;
        this.order2ProductRepository = order2ProductRepository;
    }

    public void addToOrder(String secureKod, List<String> stringIds,String orderId,String totalMoney, String pickUpPoint){
       User user = userRepository.findBySecureKod(secureKod);
       List<Order2Product> order2ProductList = new ArrayList<>();
       if (user!=null){
           //Integer bonus = Bonus.countBonus(Integer.parseInt(totalMoney));
           //user.addPoints(bonus);

           //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           //String text = LocalDateTime.now().format(formatter);

           //String[] strings = text.split("-");

           Long currentDate = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli();

           Orders order = new Orders();
           order.setDataOfOrder(currentDate);
           order.setOrderId(Integer.parseInt(orderId));
           order.setStatus("Не доставлено");
           order.setPickUpPoint(pickUpPoint);
           order.setUserId(user.getId());

           for (String productId : stringIds){
               Order2Product order2Product = new Order2Product();
               order2Product.setOrderId(Integer.parseInt(orderId));
               order2Product.setProductId(Integer.parseInt(productId.trim()));
               order2ProductList.add(order2Product);
           }

           if (order2ProductList.size() != 0){
               ordersRepository.save(order);
               //userRepository.save(user);
               order2ProductRepository.saveAll(order2ProductList);
           }
       }
    }
}
