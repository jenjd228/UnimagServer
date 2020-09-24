package com.example.demo.Controller;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.AbstractMap;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("checkBySecureKod/{secureKod}")
    public ResponseEntity checkBySecureKod(@PathVariable String secureKod) {
        String serviceResponse = userService.checkBySecureKod(secureKod);
        if (serviceResponse.equals("OK")){
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity("NOT_FOUND",HttpStatus.NOT_FOUND);
    }

    @GetMapping("getUser/{secureKod}")
    public ResponseEntity getUser(@PathVariable String secureKod){
        AbstractMap.SimpleEntry<String, UserDTO> serviceResponse = userService.getUser(secureKod);
        if (serviceResponse.getKey().equals("ERROR")){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(serviceResponse.getValue(),HttpStatus.OK);
    }

    @PostMapping("firstUpdate")
    public ResponseEntity firstUpdate(@RequestParam String email, @RequestParam String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String serviceResponse = userService.firstUpdate(email,password);
        return new ResponseEntity(serviceResponse,HttpStatus.OK);
    }

    @PostMapping("userUpdate")
    public ResponseEntity userUpdate(@RequestParam String email,@RequestParam String fio,@RequestParam String birthData) {
        String serviceResponse = userService.userUpdate(email,fio,birthData);
        if (serviceResponse.equals("OK")){
            return new ResponseEntity("OK",HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.NOT_FOUND);
    }

    @PostMapping("checkByData")
    public ResponseEntity checkByData(@RequestParam String email,@RequestParam String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String serviceResponse = userService.checkByData(email,password);
        if (serviceResponse.equals("NOT_FOUND")){
            return new ResponseEntity("NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(serviceResponse,HttpStatus.OK);
    }

    @PostMapping("checkUserForLoginIn")
    public ResponseEntity checkUserForLoginIn(@RequestParam String email,@RequestParam String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String serviceResponse = userService.checkUserForLoginIn(email,password);
        switch (serviceResponse){
            case "NOT_FOUND":{
                return new ResponseEntity("NOT_FOUND",HttpStatus.NOT_FOUND);
            }
            case "LOCKED":{
                return new ResponseEntity("LOCKED",HttpStatus.BAD_REQUEST);
            }
            default : return new ResponseEntity(serviceResponse,HttpStatus.OK);
        }
    }

}
