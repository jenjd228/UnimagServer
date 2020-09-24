package com.example.demo.Service;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.User;
import com.example.demo.Repository.BasketRepository;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class BasketService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private CatalogRepository catalogRepository;

    @Autowired
    private ModelMapper modelMapper;

    public AbstractMap.SimpleEntry<String, Object[]> getBasketList(String secureKod){
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return new AbstractMap.SimpleEntry<>("USER_NOT_FOUND", new Object[0]);
        }
        List<BasketProduct> list = basketRepository.findByUserId(user.getId());
        if (list.size()!=0){
            Object[] list3 = Objects.requireNonNull(list).stream().map(this::convertToDto).toArray();
            return new AbstractMap.SimpleEntry<>("OK", list3);
        }
        return new AbstractMap.SimpleEntry<>("ERROR", new Object[0]);
    }

    private BasketProductDTO convertToDto(BasketProduct product) {
        BasketProductDTO basketProductDTO = Objects.isNull(product.getCatalogProduct()) ? null : modelMapper.map(product.getCatalogProduct(), BasketProductDTO.class);
        basketProductDTO.setCount(product);
        return basketProductDTO;
    }

    @Transactional
    public String deleteProductFromBasket(String secureKod, Integer productId){
        User user = userRepository.findBySecureKod(secureKod);
        if (user==null){
            return "USER_NOT_FOUND";
        }

        List<BasketProduct> list = basketRepository.findByUserId(user.getId());
        if (list!=null){
            for (BasketProduct basketProduct:list){
                if (basketProduct.getProductId().equals(productId)){
                    basketRepository.deleteById(basketProduct.getId());
                    break;
                }
            }
            return "OK";
        }
        return "PRODUCT_NOT_FOUND";
    }

    public String addToBasket(String secureKod,String id){
        User user = userRepository.findBySecureKod(secureKod);
        if (user!=null){
            Optional<Catalog> product = catalogRepository.findById(Integer.valueOf(id));
            if (product.isPresent()){
                BasketProduct basketProduct = new BasketProduct();
                basketProduct.setUserId(user.getId());
                basketProduct.setProductId(product.get().getId());
                basketProduct.setCount(1);
                basketRepository.save(basketProduct);
                return "OK";
            }
            return "PRODUCT_NOT_FOUND";
        }
        return "USER_NOT_FOUND";
    }

    public String deleteOneProductFromBasket(String secureKod,String id){
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
            return "OK";
        }
        return "USER_NOT_FOUND";
    }

    public String addOneProductToBasket(String secureKod,String id){
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
            return "OK";
        }
        return "USER_NOT_FOUND";
    }

}
