package com.example.demo.Service;

import com.example.demo.Repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;

@Service
public class CatalogService {

    @Autowired
    CatalogRepository catalogRepository;
    ///"+currentNumberList +"/"+ sortByTime+"/"+sortByCategory+"/"+sortByPrice    // sortByPriceASC sortByPriceDESC
    public AbstractMap.SimpleEntry<String, Object[]> getList(Integer currentNumberList){


        long productCount = catalogRepository.count();
        PageRequest pageable = PageRequest.of(currentNumberList,8);
        if (productCount==0){
            return new AbstractMap.SimpleEntry<>("ERROR", new Object[0]);
        }
        int countGroup = (int) ((productCount/8)+1);//например 100/8 = 12.5 значит всего 13 групп
        if (currentNumberList<=countGroup){
            Object[] list = catalogRepository.findBy(pageable).toArray();
            return new AbstractMap.SimpleEntry<>("OK", list);
        }
        return new AbstractMap.SimpleEntry<>("ERROR", new Object[0]);
    }
}
