package com.example.demo.Controller;

import com.example.demo.DTO.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {
    @GetMapping("/product")
    public ResponseEntity product()
    { return new ResponseEntity( HttpStatus.OK); }

}
