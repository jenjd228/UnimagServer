package com.example.demo.Service;

import com.example.demo.Repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;

@Service
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;

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
                list = catalogRepository.findByCategoryPriceDESC(pageable,category).toArray();
            }else if (price.equals("price ASC")){
                list = catalogRepository.findByCategoryPriceASC(pageable,category).toArray();
            }else {
                list = catalogRepository.findByCategory(pageable,category).toArray();
            }
        }else {
            if (price.equals("price DESC")){
                list = catalogRepository.findByPriceDESC(pageable).toArray();
            }else if (price.equals("price ASC")){
                list = catalogRepository.findByPriceASC(pageable).toArray();
            }else {
                list = catalogRepository.findBy(pageable).toArray();
            }
        }
        return new AbstractMap.SimpleEntry<>("OK", list);
    }
}
