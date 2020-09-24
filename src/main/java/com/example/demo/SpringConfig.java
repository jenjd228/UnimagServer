package com.example.demo;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

}
