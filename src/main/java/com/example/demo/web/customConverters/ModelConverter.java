package com.example.demo.web.customConverters;

import com.example.demo.Model.Catalog;
import com.example.demo.Model.Image;
import com.example.demo.web.model.ImageDTO;
import com.example.demo.web.model.ProductForUpdateProductDTO;

public class ModelConverter {

    private static ModelConverter instance;

    private ModelConverter() {

    }

    public static ModelConverter getInstance() {
        if (instance == null) {
            instance = new ModelConverter();
        }
        return instance;
    }

    public Catalog converterProductForUpdateDTOToCatalog(ProductForUpdateProductDTO productForUpdateProductDTO, Catalog catalog){
        catalog.getListImage().clear();

        catalog.setCategory(productForUpdateProductDTO.getCategory());
        catalog.setDescriptions(productForUpdateProductDTO.getDescriptions());
        catalog.setPrice(productForUpdateProductDTO.getPrice());
        catalog.setMainImage(productForUpdateProductDTO.getMainImage());
        catalog.setTitle(productForUpdateProductDTO.getTitle());

        for (ImageDTO image : productForUpdateProductDTO.getImagePaths()){
            if (!image.isDelete()){
                catalog.getListImage().add(image.getImage());
            }
        }
        return catalog;
    }

}
