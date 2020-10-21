package com.example.demo.Controller;

import com.example.demo.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    @GetMapping("userIsSub/{secureKod}")
    public ResponseEntity getUser(@PathVariable String secureKod){
        boolean isSub = partnerService.isSubscribe(secureKod);
        if (!isSub){
            return new ResponseEntity("ACCESS_CLOSED", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("ACCESS_OPEN",HttpStatus.OK);
    }
}
