package com.example.demo.Controller;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.User;
import com.example.demo.Repository.BasketRepository;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class BasketController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private BasketRepository basketRepository;

    //@Autowired
    //private BasketController(){

    //}

    @GetMapping("getBasketList/{secureKod}")
    public ResponseEntity getBasketList(@PathVariable String secureKod){
        //Long productCount = basketRepository.count();
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity("BAD_REQUEST", HttpStatus.BAD_REQUEST);
        }
        //Integer id = user.getId();
        List<BasketProduct> list = basketRepository.findByUserId(user.getId());
        if (list.size()!=0){
            List<Integer> ids = new ArrayList<>();
            for (BasketProduct basketProduct:list){
                ids.add(basketProduct.getProductId());
            }
            List<Catalog> list2 = catalogRepository.findByUserIds(ids);
            List<BasketProductDTO> list3 = new ArrayList<>();
            for (Catalog catalog:list2){
                BasketProductDTO basketProductDTO = new BasketProductDTO(); //переделать с помощью join возможно
                basketProductDTO.setProductId(catalog.getId());
                basketProductDTO.setImageName(catalog.getImageName());
                basketProductDTO.setCategory(catalog.getCategory());
                basketProductDTO.setPrice(catalog.getPrice());
                basketProductDTO.setDescriptions(catalog.getDescriptions());
                basketProductDTO.setTitle(catalog.getTitle());
                for (BasketProduct basketProduct:list){
                    if(basketProduct.getProductId().equals(catalog.getId())){
                        basketProductDTO.setCount(basketProduct.getCount());
                        break;
                    }
                }
                list3.add(basketProductDTO);
            }
            return new ResponseEntity(list3,HttpStatus.OK);
        }
        return new ResponseEntity("Error!",HttpStatus.NOT_FOUND);
    }

    @GetMapping("deleteBasketProduct/{secureKod}/{productId}")
    public ResponseEntity deleteBasketProduct(@PathVariable String secureKod,@PathVariable Integer productId){
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        List<BasketProduct> list = basketRepository.findByUserId(user.getId());
        if (list!=null){
            for (BasketProduct basketProduct:list){
                if (basketProduct.getProductId()==productId){
                    basketRepository.deleteById(basketProduct.getId());
                    break;
                }
            }
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("addToBasket")
    public ResponseEntity addToBasket(@RequestParam String id, @RequestParam String secureKod) { // не правильно прописана логика
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            Optional<Catalog> product = catalogRepository.findById(Integer.valueOf(id));
            if (product.isPresent()){
                BasketProduct basketProduct = new BasketProduct();
                basketProduct.setUserId(user.getId());
                basketProduct.setProductId(product.get().getId());
                basketProduct.setCount(1);
                basketRepository.save(basketProduct);
                return new ResponseEntity("ok",HttpStatus.OK);
            }
           /* List<BasketProduct> basketProduct1 = basketRepository.findByUserId(user.getId());
            if (basketProduct1.size()!=0){
                for (BasketProduct basketProduct:basketProduct1){
                    if (basketProduct.getId().toString().equals(id)){
                        basketProduct.setCount(basketProduct.getCount()+1);
                        basketRepository.save(basketProduct);
                        break;
                    }
                }
                return new ResponseEntity("ok",HttpStatus.OK);
            }*/
            return new ResponseEntity("PRODUCT_NOT_FOUND",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("USER_NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("deleteOneProductFromBasket")
    public ResponseEntity deleteOneProductFromBasket(@RequestParam String id,@RequestParam String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            List<BasketProduct> basketProduct1 = basketRepository.findByUserId(user.getId());
            for (BasketProduct basketProduct:basketProduct1){
                if (basketProduct.getProductId().toString().equals(id)){
                    basketProduct.setCount(basketProduct.getCount()-1);
                    basketRepository.save(basketProduct);
                    break;
                }
            }
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity("USER_NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("addOneProductToBasket")
    public ResponseEntity addOneProductToBasket(@RequestParam String id,@RequestParam String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            List<BasketProduct> basketProduct1 = basketRepository.findByUserId(user.getId());
            for (BasketProduct basketProduct:basketProduct1){
                if (basketProduct.getProductId().toString().equals(id)){
                    basketProduct.setCount(basketProduct.getCount()+1);
                    basketRepository.save(basketProduct);
                    break;
                }
            }
            return new ResponseEntity("ok",HttpStatus.OK);
        }
        return new ResponseEntity("USER_NOT_FOUND",HttpStatus.BAD_REQUEST);
    }

}
