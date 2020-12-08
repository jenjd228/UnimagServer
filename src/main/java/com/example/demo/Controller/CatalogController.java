package com.example.demo.Controller;

import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.AbstractMap;

@RestController
public class CatalogController {

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private CatalogService catalogService;

    @GetMapping("getCatalogSize")
    public ResponseEntity getCatalogSize(){
        return new ResponseEntity(catalogRepository.count(), HttpStatus.OK);
    }

    @GetMapping("getList/{currentNumberList}/{category}/{price}/{whereFlag}")
    public ResponseEntity getList(@PathVariable Integer currentNumberList,@PathVariable String category,@PathVariable String price,@PathVariable String whereFlag){
        AbstractMap.SimpleEntry<String, Object[]> serviceResponse = catalogService.getList(currentNumberList,category,price,whereFlag);
        if ("OK".equals(serviceResponse.getKey())) {
            return new ResponseEntity(serviceResponse.getValue(), HttpStatus.OK);
        }
        return new ResponseEntity("Error!", HttpStatus.BAD_REQUEST);
    }
}
