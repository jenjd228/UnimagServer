package com.example.demo.web.service;

import com.example.demo.Model.Catalog;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.ImagesRepository;
import com.example.demo.web.customConverters.ModelConverter;
import com.example.demo.web.model.DeleteProductDTO;
import com.example.demo.web.model.ImageDTO;
import com.example.demo.web.model.ProductForAddProductDTO;
import com.example.demo.web.model.ProductForUpdateProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class WebCatalogService {

    private final CatalogRepository catalogRepository;

    private final ImagesRepository imagesRepository;

    WebCatalogService(ImagesRepository imagesRepository,CatalogRepository catalogRepository) {
        this.imagesRepository = imagesRepository;
        this.catalogRepository = catalogRepository;
    }

    public void productCreate(ProductForAddProductDTO productDTO) {
        //MultipartFile file = productDTO.getFile();
        Catalog catalog = new Catalog();

        /*if (file.getSize() > 5242880) {

        }

        String symbols = "abcdefghijklmnopqrstuvwxyz";
        String random = new Random().ints(6, 0, symbols.length())
                .mapToObj(symbols::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());

        String first = random.substring(0, 2);
        String second = random.substring(2, 4);
        String third = random.substring(4, 6);

        String dirStr = first + "/" + second + "/" + third;
        String imageLoc = dirStr + "/" + UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dir = new File("upload/" + dirStr);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            File img = new File("upload/" + imageLoc);
            img.createNewFile();
            file.transferTo(img.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        catalog.setCategory(productDTO.getCategory());
        catalog.setDate(LocalDateTime.now().toInstant(ZoneOffset.UTC).getEpochSecond());
        catalog.setDescriptions(productDTO.getDescriptions());
        catalog.setPrice(productDTO.getPrice());
        catalog.setTitle(productDTO.getTitle());
        catalog.setMainImage("https://docs.google.com/uc?id=1tMkSCtoDyhADnHTkq6nUe2vYoi46UgVW");
        catalog.setHash(UUID.randomUUID().toString());
        catalogRepository.save(catalog);
    }

    public void updateAndDeleteProduct(ProductForUpdateProductDTO productForUpdateProductDTO){
        Catalog catalogFromBd = catalogRepository.findCatalogByHashContaining(productForUpdateProductDTO.getHash());
        Catalog newCatalog = ModelConverter.getInstance().converterProductForUpdateDTOToCatalog(productForUpdateProductDTO,catalogFromBd);

        catalogRepository.save(newCatalog);

        for (ImageDTO image : productForUpdateProductDTO.getImagePaths()){
            if (image.isDelete()){
                imagesRepository.deleteByKeyImageNameAndKeyProductId(image.getImage().getKey().getImageName(),image.getImage().getKey().getProductId());
            }
        }
    }

    public void deleteProduct(DeleteProductDTO deleteProductDTO){
        catalogRepository.deleteByHash(deleteProductDTO.getHash());
    }

}
