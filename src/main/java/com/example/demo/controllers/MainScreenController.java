package com.example.demo.controllers;
import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainScreenController {
    public PartService partService;
    public ProductService productService;
    private List<Part> partList;
    private List<Product> productList;

    public MainScreenController(PartService partService, ProductService productService){
        this.partService=partService;
        this.productService=productService;
    }

    @GetMapping("/home")
    public String display(Model model, @RequestParam(value = "partkeyword") String partKeyword, @RequestParam(value = "productkeyword") String productKeyword) {
        List<Part> allParts = partService.searchPart(partKeyword);
        List<Product> allProducts = productService.searchProduct(productKeyword);

        // Filter parts by keyword if provided
        if (partKeyword != null && !partKeyword.isEmpty()) {
            allParts = allParts.stream()
                    .filter(part -> part.getName().toLowerCase().contains(partKeyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (productKeyword != null && !productKeyword.isEmpty()) {
            allProducts = allProducts.stream()
                    .filter(product -> product.getName().toLowerCase().contains(productKeyword.toLowerCase()))
                    .collect(Collectors.toList());
        }

        model.addAttribute("parts", allParts);
        model.addAttribute("products", allProducts);
        model.addAttribute("partkeyword", partKeyword);
        model.addAttribute("productkeyword", productKeyword);

        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

}
