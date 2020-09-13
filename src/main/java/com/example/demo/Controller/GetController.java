package com.example.demo.Controller;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.User;
import com.example.demo.Repository.BasketRepository;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.UserRepository;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
public class GetController {

    //@Autowired
    //private ModelMapper modelMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BasketRepository basketRepository;

    @GetMapping("checkBySecureKod/{secureKod}")
    public ResponseEntity checkBySecureKod(@PathVariable String secureKod) {
        //@RequestParam String message, @RequestParam String mail
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            return new ResponseEntity("ok", HttpStatus.OK);
        }
        return new ResponseEntity("NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("getUser/{secureKod}")
    public ResponseEntity getUser(@PathVariable String secureKod){
        //JSONObject json = new JSONObject();
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }else {

            return new ResponseEntity(new UserDTO(user.getEmail(),user.getFio(),user.getPoints()),HttpStatus.OK);
        }
    }

    /*private UserDTO convertToDto(User user) {
        UserDTO userDTO =  Objects.isNull(user) ? null : modelMapper.map(user,UserDTO.class);;
        return userDTO;
    }*/

    @GetMapping("getList/{currentNumberList}")
    public ResponseEntity getList(@PathVariable Integer currentNumberList){
        Long productCount = catalogRepository.count();
        PageRequest pageable = PageRequest.of(currentNumberList,8, Sort.Direction.ASC,"date");
        if (productCount==0){
            return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
        }
        int countGroup = (int) ((productCount/8)+1);//например 100/8 = 12.5 значит всего 13 групп
        if (currentNumberList<=countGroup){
            List<Catalog> list = catalogRepository.findBy(pageable);
            return new ResponseEntity(list,HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.BAD_REQUEST);
    }



}
