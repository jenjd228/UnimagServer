package com.example.demo.confic;

import com.example.demo.DTO.*;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.Orders;
import com.example.demo.web.model.ProductForAddProductDTO;
import com.example.demo.web.model.ProductForUpdateProductDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapperToBasketProductDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Catalog, BasketProductDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setProductId(source.getId());
            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperForCatalogDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Catalog, CatalogDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setListImageDTO(source.getListImage());
            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToOrderDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Orders, OrdersDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setLocalDate(source.getDataOfOrder());
            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperToOrderToProductDTO() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Order2ProductInterface, Order2ProductDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {

            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

    //WEB

    @Bean
    public ModelMapper modelMapperProductForAddProductDTOToCatalog() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<ProductForAddProductDTO, Catalog> propertyMap = new PropertyMap<>() {
            protected void configure() {

            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

    @Bean
    public ModelMapper modelMapperCatalogToProduct() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Catalog, ProductForUpdateProductDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setCategory(source.getCategory());
                map().setMainImage(source.getMainImage());
                map().setDescriptions(source.getDescriptions());
                map().setPrice(source.getPrice());
                map().setTitle(source.getTitle());
                map().setImagePathsByImageDTO(source.getListImage());
            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

}
