package com.example.demo.web.wedControllers;

import com.example.demo.Model.Catalog;
import com.example.demo.Repository.CatalogRepository;
import com.example.demo.Service.CatalogService;
import com.example.demo.managers.MyHashMapManager;
import com.example.demo.web.model.Category;
import com.example.demo.web.model.DeleteProductDTO;
import com.example.demo.web.model.ProductForUpdateProductDTO;
import com.example.demo.web.service.AuthService;
import com.example.demo.web.service.SearchService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;

@Controller
public class SearchController {

    private final CatalogService catalogService;

    private final HashMap<Integer, Category> allCategoriesMap;

    private final AuthService authService;

    private final SearchService searchService;

    SearchController(AuthService authService, HashMap<Integer, Category> allCategoriesMap, SearchService searchService, CatalogService catalogService) {
        this.authService = authService;
        this.allCategoriesMap = allCategoriesMap;
        this.searchService = searchService;
        this.catalogService = catalogService;
    }

    @GetMapping("/search/findProducts")
    public String filter(Model model, @RequestParam(value = "filter") String filter) {
        if (!authService.check()) {
            return "redirect:/login";
        }
        List<Catalog> products = catalogService.getCatalogsByRegex(filter);
        model.addAttribute("productList", products);
        return "frameSearch";
    }

    @GetMapping("/searchByHash")
    public String searchByHash(Model model, @RequestParam(value = "hash") String hash, RedirectAttributes redirectAttributes) {
        if (!authService.check()) {
            return "redirect:/login";
        }
        if (hash == null || hash.isEmpty()) {
            redirectAttributes.addFlashAttribute("searchByHashMessage", "Hash не должен быть пустой");
            return "redirect:/updateProduct";
        }

        ProductForUpdateProductDTO product = searchService.findProductForUpdateProductDTOByHash(hash);

        model.addAttribute("delete", new DeleteProductDTO(product.getHash()));
        model.addAttribute("product", product);
        model.addAttribute("categories", MyHashMapManager.getInstance().cloneHashMapAndSetSelected(allCategoriesMap, product.getCategory()));
        return "frameUpdateProduct";
    }

    @GetMapping("/search")
    public String search() {
        if (!authService.check()) {
            return "redirect:/login";
        }
        return "frameSearch";
    }
}
