package com.example.demo.Controller;

import com.example.demo.DTO.Order2ProductDTO;
import com.example.demo.Model.User;
import com.example.demo.Repository.PickUpPointRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DefaultController {

    private final UserRepository userRepository;

    private final OrderService orderService;

    private final PickUpPointRepository pickUpPointRepository;

    public DefaultController(UserRepository userRepository, OrderService orderService, PickUpPointRepository pickUpPointRepository) {
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.pickUpPointRepository = pickUpPointRepository;
    }

    @GetMapping("getOrdersList/{secureKod}")
    public ResponseEntity getOrdersList(@PathVariable String secureKod) {
        Object[] orders = orderService.getOrdersList(secureKod);
        if (orders.length == 0) {
            return new ResponseEntity(orders, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orders, HttpStatus.OK);
    }

    @GetMapping("getOrderToProductList/{secureKod}/{orderId}")
    public ResponseEntity getOrderToProductList(@PathVariable String secureKod,@PathVariable Integer orderId) {
        List<Order2ProductDTO> productDTOS = orderService.getProductListByOrderId(secureKod,orderId);
        if (productDTOS == null) {
            return new ResponseEntity("Ошибка", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(productDTOS, HttpStatus.OK);
    }

    @PostMapping("addToOrders")
    public ResponseEntity addToOrders(@RequestParam String productList, @RequestParam String secureKod, @RequestParam String orderId, @RequestParam String totalMoney, @RequestParam String pickUpPoint) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        orderService.addToOrder(secureKod, productList, orderId, totalMoney, pickUpPoint);
        return new ResponseEntity("ok", HttpStatus.OK);
    }

    @GetMapping("getPickUpPointList")
    public ResponseEntity getPickUpPointList() {
        List<String> list = new ArrayList();
        pickUpPointRepository.findAll().forEach(it -> list.add(it.getPickUpPoint()));
        return new ResponseEntity(list, HttpStatus.OK);
    }



    /*@PostMapping("sendMessage")
    public ResponseEntity sendMessage(@RequestParam String email) {
        //@RequestParam String message, @RequestParam String mail
        String kod = String.valueOf(new Random().nextInt(9999));
        EmailKod emailKod = new EmailKod();
        emailKod.setEmail(email);
        emailKod.setKod(kod);
        User user = userRepository.findByEmail(email);
        if (user==null){
            sendEmail.sendEmail(kod,email);
            emailKodRepository.save(emailKod);
            return new ResponseEntity("no",HttpStatus.OK);
        }
        return new ResponseEntity("yes",HttpStatus.OK);
    }*/
}
