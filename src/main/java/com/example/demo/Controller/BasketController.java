package com.example.demo.Controller;

import com.example.demo.Service.BasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.AbstractMap;

@RestController
public class BasketController {

    @Autowired
    private BasketService basketService;

    @GetMapping("getBasketList/{secureKod}")
    public ResponseEntity getBasketList(@PathVariable String secureKod){
        AbstractMap.SimpleEntry<String, Object[]> serviceResponse = basketService.getBasketList(secureKod);
        switch (serviceResponse.getKey()){
            case "OK": return new ResponseEntity(serviceResponse.getValue(),HttpStatus.OK);
            case "USER_NOT_FOUND": return new ResponseEntity("USER_NOT_FOUND",HttpStatus.NOT_FOUND);
            default: return new ResponseEntity("ERROR",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("deleteBasketProduct/{secureKod}/{productHash}")
    public ResponseEntity deleteBasketProduct(@PathVariable String secureKod,@PathVariable String productHash){
        String serviceResponse = basketService.deleteProductFromBasket(secureKod,productHash);
        switch (serviceResponse){
            case "OK": return new ResponseEntity("ok",HttpStatus.OK);
            case "USER_NOT_FOUND": return new ResponseEntity("USER_NOT_FOUND",HttpStatus.NOT_FOUND);
            default: return new ResponseEntity("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("addToBasket")
    public ResponseEntity addToBasket(@RequestParam String productHash, @RequestParam String color, String size, @RequestParam String secureKod) {
        String serviceResponse;
        if (size.equals("null")) {
            serviceResponse = basketService.addToBasket(secureKod, productHash, color, null);
        } else {
            serviceResponse = basketService.addToBasket(secureKod, productHash, color, size);
        }
        switch (serviceResponse){
            case "OK": return new ResponseEntity("OK",HttpStatus.OK);
            case "USER_NOT_FOUND": return new ResponseEntity("USER_NOT_FOUND",HttpStatus.NOT_FOUND);
            case "PRODUCT_IS_PRESENT" : return new ResponseEntity("PRODUCT_IS_PRESENT",HttpStatus.OK);
            default: return new ResponseEntity("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("deleteOneProductFromBasket")
    public ResponseEntity deleteOneProductFromBasket(@RequestParam String productHash,@RequestParam String secureKod) {
        String serviceResponse = basketService.deleteOrAddOneProductFromBasket(secureKod,productHash,false);
        if ("OK".equals(serviceResponse)) {
            return new ResponseEntity("ok", HttpStatus.OK);
        }
        return new ResponseEntity("USER_NOT_FOUND", HttpStatus.NOT_FOUND);
    }

    @PostMapping("addOneProductToBasket")
    public ResponseEntity addOneProductToBasket(@RequestParam String productHash,@RequestParam String secureKod) {
        String serviceResponse = basketService.deleteOrAddOneProductFromBasket(secureKod,productHash,true);
        if ("OK".equals(serviceResponse)) {
            return new ResponseEntity("ok", HttpStatus.OK);
        }
        return new ResponseEntity("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
    }

}
