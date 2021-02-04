package com.example.demo.web;

import com.example.demo.Model.Catalog;
import com.example.demo.Model.Image;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Repository.ImagesRepository;
import com.example.demo.managers.MyHashMapManager;
import com.example.demo.web.customConverters.ModelConverter;
import com.example.demo.web.model.*;
import com.example.demo.web.service.WebCatalogService;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class ProductUpdateAddDeleteController {

    private final HashMap<Integer, Category> allCategoriesMap;

    private final WebCatalogService webCatalogService;

    private final CatalogRepository catalogRepository;

    private final ImagesRepository imagesRepository;

    ProductUpdateAddDeleteController(ImagesRepository imagesRepository, CatalogRepository catalogRepository, WebCatalogService webCatalogService, HashMap<Integer, Category> allCategoriesMap){
        this.imagesRepository = imagesRepository;
        this.catalogRepository = catalogRepository;
        this.webCatalogService = webCatalogService;
        this.allCategoriesMap = allCategoriesMap;
    }

    @GetMapping("/updateProduct")
    public String updateProduct(Model model){
        model.addAttribute("product",new ProductForUpdateProductDTO());
        model.addAttribute("categories",allCategoriesMap);
        return "frameUpdateProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(Model model,@Valid @ModelAttribute("product") ProductForUpdateProductDTO productForUpdateProductDTO,BindingResult bindingResult){
        HashMap<Integer, Category> newSelectedMap = MyHashMapManager.getInstance().cloneHashMapAndSetSelected(allCategoriesMap, productForUpdateProductDTO.getCategory());
        model.addAttribute("categories", newSelectedMap);
        if (bindingResult.hasErrors()){
            return "frameUpdateProduct";
        }
        for (ImageDTO imageDTO : productForUpdateProductDTO.getImagePaths()){
            if (imageDTO.getImage().getKey().getImageName() == null || imageDTO.getImage().getKey().getImageName().isEmpty()){
                model.addAttribute("googleImagePathErrors","Пути к картинкам не должны быть пустыми");
                return "frameUpdateProduct";
            }
        }

        Catalog catalogFromBd = catalogRepository.findCatalogByHashContaining(productForUpdateProductDTO.getHash());
        Catalog newCatalog = ModelConverter.getInstance().converterProductForUpdateDTOToCatalog(productForUpdateProductDTO,catalogFromBd);

        catalogRepository.save(newCatalog);

        for (ImageDTO image : productForUpdateProductDTO.getImagePaths()){
            if (image.isDelete()){
                imagesRepository.deleteByKeyImageNameAndKeyProductId(image.getImage().getKey().getImageName(),image.getImage().getKey().getProductId());
            }
        }


        model.addAttribute("product",new ProductForUpdateProductDTO());
        return "frameUpdateProduct";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model){
        model.addAttribute("categories",allCategoriesMap);
        model.addAttribute("product",new ProductForAddProductDTO());
        return "frameAddProduct";
    }

    @PostMapping("/productCreate")
    public String handleFileUpload(@Valid @ModelAttribute("product") ProductForAddProductDTO product, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) throws JSONException {
        HashMap<Integer, Category> newSelectedMap = MyHashMapManager.getInstance().cloneHashMapAndSetSelected(allCategoriesMap, product.getCategory());
        model.addAttribute("categories", newSelectedMap);

        if (bindingResult.hasErrors()) {
            return "frameAddProduct";
        }
        /*if (isFileNullOrEmpty(product.getFile())) {
            model.addAttribute("fileMessage", "Фото товара не должно быть пустым");
            return "frameAddProduct";
        }
        if (!isFileFormatJpgOrPng(product.getFile())) {
            model.addAttribute("fileMessage", "Фото товара должно быть только png или jpg формата");
            return "frameAddProduct";
        }*/
        webCatalogService.productCreate(product);
        redirectAttributes.addFlashAttribute("success", "Товар успешно добавлен!!");
        return "redirect:/addProduct";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@ModelAttribute("delete") DeleteProductDTO deleteProductDTO){
        catalogRepository.deleteByHash(deleteProductDTO.getHash());
        return "redirect:/updateProduct";
    }

    /*private boolean isFileNullOrEmpty(MultipartFile file) {
        return file == null || file.isEmpty();
    }

    private boolean isFileFormatJpgOrPng(MultipartFile file) {

        String extension = FilenameUtils.getExtension(file.getOriginalFilename());

        if (extension != null) {
            boolean jpg = extension.equals("jpg");
            boolean png = extension.equals("png");
            return jpg || png;
        } else {
            return false;
        }
    }*/
}
