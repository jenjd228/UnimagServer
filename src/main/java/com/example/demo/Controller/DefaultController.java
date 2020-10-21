package com.example.demo.Controller;

import com.example.demo.Model.Orders;
import com.example.demo.Model.Partner;
import com.example.demo.Model.User;
import com.example.demo.Repository.*;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrdersRepository ordersRepository;


    @GetMapping("getPartner")
    public ResponseEntity getPartner(){
        List<Partner> list = (List<Partner>) partnerRepository.findAll();
        if (list.isEmpty()){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(list,HttpStatus.OK);
        }
    }

    @GetMapping("getOrdersList/{secureKod}")
    public ResponseEntity getOrdersList(@PathVariable String secureKod){
        Object[] orders = userService.getOrdersList(secureKod);
        System.out.println(Arrays.toString(orders));
        if (orders.length == 0){
            return new ResponseEntity(orders,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(orders,HttpStatus.OK);
    }

    @PostMapping("addToOrders")
    public ResponseEntity addToOrders(@RequestParam String stringIds, @RequestParam String secureKod, @RequestParam String orderId, @RequestParam String totalMoney) {
        stringIds = stringIds.replaceAll("\\[","").replaceAll("]",""); // из-за преобразований в строку пойвляются лишние скобки
        List<String> list = Arrays.asList(stringIds.split(","));
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        orderService.addToOrder(secureKod,list,orderId,totalMoney);
        /*Order order;
        for (String id:list1){
            order = new Order();
            order.setProductId(Integer.valueOf(id));
            order.setDataOfOrder(LocalDateTime.now());
            order.setUserId(user.getId());
            order.setStatus("Не доставлено");
            ordersRepository.save(order);
        }*/
        return new ResponseEntity("ok",HttpStatus.OK);
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
