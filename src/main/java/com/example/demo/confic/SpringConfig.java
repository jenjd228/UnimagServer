package com.example.demo.confic;

import com.example.demo.DTO.*;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.Orders;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@ComponentScan(basePackages = "com.example.demo.Controller")
public class SpringConfig {

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

}
