package com.example.demo.confic;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.DTO.OrdersDTO;
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
    public ModelMapper modelMapper() {
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
    public ModelMapper modelMapper2() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<Orders, OrdersDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                map().setLocalDate(source.getDataOfOrder());
            }
        };
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

}
