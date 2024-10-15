package com.example.demo.controllers;

import com.example.demo.DataObjects.Part;
import com.example.demo.DataObjects.Product;
import com.example.demo.Service.PartService;
import com.example.demo.Service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MainScreenController {
    private static final Logger logger = LoggerFactory.getLogger(MainScreenController.class);
    private final PartService partService;
    private final ProductService productService;

    public MainScreenController(PartService partService, ProductService productService){
        this.partService = partService;
        this.productService = productService;
    }

    @GetMapping("/home")
    public String display(Model model, @RequestParam(value = "partkeyword", defaultValue = "") String partKeyword, @RequestParam(value = "productkeyword", defaultValue = "") String productKeyword) {
        List<Part> allParts = partService.searchPart(partKeyword);
        List<Product> allProducts = productService.searchProduct(productKeyword);

        // Filter parts by keyword
        allParts = allParts.stream()
                .filter(part -> part.getName() != null && part.getName().toLowerCase().contains(partKeyword.toLowerCase()))
                .collect(Collectors.toList());

        allProducts = allProducts.stream()
                .filter(product -> product.getName() != null && product.getName().toLowerCase().contains(productKeyword.toLowerCase()))
                .collect(Collectors.toList());

        model.addAttribute("parts", allParts);
        model.addAttribute("products", allProducts);
        model.addAttribute("partkeyword", partKeyword);
        model.addAttribute("productkeyword", productKeyword);

        logger.info("Displaying home with {} parts and {} products", allParts.size(), allProducts.size());
        return "home";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        model.addAttribute("errorMessage", e.getMessage());
        logger.error("An error occurred: {}", e.getMessage(), e);
        return "error";
    }
}
