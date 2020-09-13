package com.example.demo;

import com.example.demo.DTO.UserDTO;
import com.example.demo.Model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.example.demo.Controller")
public class SpringConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        PropertyMap<User, UserDTO> propertyMap = new PropertyMap<>() {
            protected void configure() {
                //map().setAnnounce(source.getText());
                //map().setTimestamp(source.getTime());
            }
        };
        //modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.addMappings(propertyMap);

        return modelMapper;
    }

}
