package com.example.demo.Service;

import com.example.demo.DTO.BasketProductDTO;
import com.example.demo.DTO.CatalogDTO;
import com.example.demo.DTO.ImageDTO;
import com.example.demo.Model.BasketProduct;
import com.example.demo.Model.Catalog;
import com.example.demo.Repository.CatalogRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Objects;

@Service
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    private ModelMapper modelMapperForCatalogDTO;

    public AbstractMap.SimpleEntry<String, Object[]> getList(Integer currentNumberList , String category,String price, String whereFlag){
        PageRequest pageable = PageRequest.of(currentNumberList,6);
        Object[] list;

        //System.out.println(currentNumberList+"\n"+category+"\n"+price+"\n"+whereFlag);

        long productCount = catalogRepository.count();
        int countGroup = (int) ((productCount/6)+1);//например 100/8 = 12.5 значит всего 13 групп
        if (productCount==0 || currentNumberList>=countGroup){
            return new AbstractMap.SimpleEntry<>("ERROR", new Object[0]);
        }

        if (whereFlag.equals("true")){
            if (price.equals("price DESC")){
                list = catalogRepository.findByCategoryPriceDESC(pageable,category).stream().map(this::convertToCatalogDto).toArray();
            }else if (price.equals("price ASC")){
                list = catalogRepository.findByCategoryPriceASC(pageable,category).stream().map(this::convertToCatalogDto).toArray();
            }else {
                list = catalogRepository.findByCategory(pageable,category).stream().map(this::convertToCatalogDto).toArray();
            }
        }else {
            if (price.equals("price DESC")){
                list = catalogRepository.findByPriceDESC(pageable).stream().map(this::convertToCatalogDto).toArray();
            }else if (price.equals("price ASC")){
                list = catalogRepository.findByPriceASC(pageable).stream().map(this::convertToCatalogDto).toArray();
            }else {
                list = catalogRepository.findBy(pageable).stream().map(this::convertToCatalogDto).toArray();
            }
        }

        return new AbstractMap.SimpleEntry<>("OK", list);
    }

    private CatalogDTO convertToCatalogDto(Catalog product) {
        return Objects.isNull(product) ? null : modelMapperForCatalogDTO.map(product, CatalogDTO.class);
    }
}
