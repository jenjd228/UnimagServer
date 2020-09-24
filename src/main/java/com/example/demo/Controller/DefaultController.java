package com.example.demo.Controller;

import com.example.demo.Model.Orders;
import com.example.demo.Model.Partner;
import com.example.demo.Model.User;
import com.example.demo.Repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@RestController
public class DefaultController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartnerRepository partnerRepository;

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

    @PostMapping("addToOrders")
    public ResponseEntity addToOrders(@RequestParam String stringIds, @RequestParam String secureKod) throws JsonProcessingException {
        Calendar currentDate = Calendar.getInstance();
        List<String> list1 = Arrays.asList(stringIds.split(","));
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        Orders order;
        for (String id:list1){
            order = new Orders();
            order.setProductId(Integer.valueOf(id));
            order.setDataOfOrder(currentDate.getTime());
            order.setUserId(user.getId());
            order.setStatus("Не доставлено");
            ordersRepository.save(order);
        }
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
