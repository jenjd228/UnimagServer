package com.example.demo.Service;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.User;
import com.example.demo.Repository.BasketRepository;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.AbstractMap;
import java.util.List;
import java.util.Objects;

@Service
public class BasketService {

    private final BasketRepository basketRepository;

    private final UserRepository userRepository;

    @Qualifier("modelMapperToBasketProductDTO")
    private final ModelMapper modelMapperToBasketProductDTO;

    private final CatalogRepository catalogRepository;

    BasketService(CatalogRepository catalogRepository,BasketRepository basketRepository, UserRepository userRepository, ModelMapper modelMapperToBasketProductDTO) {
        this.catalogRepository = catalogRepository;
        this.basketRepository = basketRepository;
        this.userRepository = userRepository;
        this.modelMapperToBasketProductDTO = modelMapperToBasketProductDTO;
    }

    public AbstractMap.SimpleEntry<String, Object[]> getBasketList(String secureKod) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user == null) {
            return new AbstractMap.SimpleEntry<>("USER_NOT_FOUND", new Object[0]);
        }

        List<BasketProduct> list = user.getBasketProducts();
        if (list.size() != 0) {
            Object[] list3 = Objects.requireNonNull(list).stream().map(this::convertToDto).toArray();
            return new AbstractMap.SimpleEntry<>("OK", list3);
        }
        return new AbstractMap.SimpleEntry<>("ERROR", new Object[0]);
    }

    private BasketProductDTO convertToDto(BasketProduct product) {
        BasketProductDTO basketProductDTO = Objects.isNull(product.getCatalogProduct()) ? null : modelMapperToBasketProductDTO.map(product.getCatalogProduct(), BasketProductDTO.class);
        basketProductDTO.setCount(product.getCount());
        basketProductDTO.setColor(product.getColor());
        basketProductDTO.setSize(product.getSize());
        return basketProductDTO;
    }

    @Transactional
    public String deleteProductFromBasket(String secureKod, String productHash) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user == null) {
            return "USER_NOT_FOUND";
        }
        List<BasketProduct> list = user.getBasketProducts();
        if (list != null) {
            for (BasketProduct basketProduct : list) {
                if (basketProduct.getProductHash().equals(productHash)) {
                    basketRepository.deleteById(basketProduct.getId());
                    break;
                }
            }
            return "OK";
        }
        return "PRODUCT_NOT_FOUND";
    }

    public String addToBasket(String secureKod, String productHash, String color, String size) {
        User user = userRepository.findBySecureKod(secureKod);
        Catalog catalog = catalogRepository.findCatalogByHashContaining(productHash);
        if (user != null && catalog != null) {

            List<BasketProduct> products = user.getBasketProducts();
            BasketProduct currentBasketProduct = basketProductCreate(productHash,user.getId(),catalog.getId(),color,size);

            if (products.size() != 0) {
                    if (products.contains(currentBasketProduct)) {
                        return "PRODUCT_IS_PRESENT";
                    }
            }
            basketRepository.save(currentBasketProduct);
            return "OK";
        }
        return "USER_NOT_FOUND";
    }

    private BasketProduct basketProductCreate(String productHash,Integer userId,Integer productId,String color,String size){
        BasketProduct basketProduct = new BasketProduct();
        basketProduct.setProductHash(productHash);
        basketProduct.setUserId(userId);
        basketProduct.setProductId(productId);
        basketProduct.setCount(1);
        basketProduct.setColor(color);
        basketProduct.setSize(size);
        return basketProduct;
    }

    public String deleteOrAddOneProductFromBasket(String secureKod, String productHash, boolean flag) {
        User user = userRepository.findBySecureKod(secureKod);
        if (user != null) {
            for (BasketProduct basketProduct : user.getBasketProducts()) {
                if (basketProduct.getProductHash().equals(productHash)) {
                    if (flag){
                        basketProduct.setCount(basketProduct.getCount() + 1);
                    }else {
                        basketProduct.setCount(basketProduct.getCount() - 1);
                    }
                    basketRepository.save(basketProduct);
                    break;
                }
            }
            return "OK";
        }
        return "USER_NOT_FOUND";
    }

}
