package com.example.demo.Controller;

import com.example.demo.Model.Partner;
import com.example.demo.Repository.PartnerRepository;
import com.example.demo.Service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PartnerController {

    private final PartnerService partnerService;

    private final PartnerRepository partnerRepository;

    PartnerController(PartnerService partnerService,PartnerRepository partnerRepository){
        this.partnerRepository = partnerRepository;
        this.partnerService = partnerService;
    }

    @GetMapping("userIsSub/{secureKod}")
    public ResponseEntity getUser(@PathVariable String secureKod){
        boolean isSub = partnerService.isSubscribe(secureKod);
        if (!isSub){
            return new ResponseEntity("ACCESS_CLOSED", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("ACCESS_OPEN",HttpStatus.OK);
    }

    @GetMapping("getPartner")
    public ResponseEntity getPartner(){
        List<Partner> list = (List<Partner>) partnerRepository.findAll();
        if (list.isEmpty()){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity(list,HttpStatus.OK);
        }
    }
}
