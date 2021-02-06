package com.example.demo.web.service;

import com.example.demo.Model.Catalog;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.web.model.ProductForUpdateProductDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final CatalogRepository catalogRepository;

    private final ModelMapper modelMapperCatalogToProduct;

    SearchService(ModelMapper modelMapperCatalogToProduct,CatalogRepository catalogRepository){
        this.catalogRepository = catalogRepository;
        this.modelMapperCatalogToProduct = modelMapperCatalogToProduct;
    }

    public ProductForUpdateProductDTO findProductForUpdateProductDTOByHash(String hash){
        Catalog catalog = catalogRepository.findCatalogByHashContaining(hash);
        ProductForUpdateProductDTO product = modelMapperCatalogToProduct.map(catalog, ProductForUpdateProductDTO.class);
        return product;
    }
}
