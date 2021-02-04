package com.example.demo.Repository;

import com.example.demo.Model.Image;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ImagesRepository extends CrudRepository<Image,Integer> {

    @Transactional
    @Modifying
    void deleteByKeyImageNameAndKeyProductId(String imageName,Integer productId);
}
