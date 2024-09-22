package com.example.demo.controllers;

import com.example.demo.domain.Part;
import com.example.demo.domain.Product;
import com.example.demo.service.PartService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ApplicationContext context;
    private final PartService partService;
    private static Product currentProduct;
    private Product product;

    private List<Part> getAvailableParts() {
        return new ArrayList<>(partService.getAll());
    }

    @GetMapping("/addProductForm")
    public String addProductForm(Model model) {
        product = null;
        model.addAttribute("parts", partService.getAll());
        Product product = new Product();

        model.addAttribute("product", product);
        model.addAttribute("availparts",getAvailableParts());
        model.addAttribute("assparts",product.getPart());
        return "productForm";
    }

    @PostMapping("/addProductForm")
    public String submitForm(@Valid @ModelAttribute("product") Product product, BindingResult dataBinding, Model model) {
        model.addAttribute("product", product);

        if(dataBinding.hasErrors()){
            ProductService productService = context.getBean(ProductServiceImpl.class);
            Product product2=productService.searchById((int)product.getId());
            model.addAttribute("parts", partService.getAll());
            model.addAttribute("availparts",getAvailableParts());
            model.addAttribute("assparts",product2.getPart());
            return "productForm";
        } else {
            ProductService repo = context.getBean(ProductServiceImpl.class);
            repo.save(product);
            currentProduct = product;  // Assign the saved product to currentProduct
            return "redirect:/mainscreen";
        }
    }

    @GetMapping("/UpdateProductForm")
    public String UpdateProductForm(@RequestParam("productID") int Id, Model model) {
        model.addAttribute("parts", partService.getAll());
        ProductService repo = context.getBean(ProductServiceImpl.class);
        Product newProduct = repo.searchById(Id);
        currentProduct=newProduct;
        //set the employ as a model attribute to prepopulate the form
        model.addAttribute("product", newProduct);
        model.addAttribute("assparts",newProduct.getPart());
        model.addAttribute("availparts",getAvailableParts());
        //send over to our form
        return "productForm";
    }

    @GetMapping("/deleteproduct")
    public String deleteProduct(@RequestParam("productID") int Id) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product productToDelete = productService.searchById(Id);
        // Disassociate parts from the product
        for (Part part : productToDelete.getPart()) { //for part of product
            part.getProducts().remove(productToDelete);
            partService.save(part);
        }
        // Remove all parts from the product
        productToDelete.getPart().clear();
        productService.save(productToDelete); // Save updated product without parts
        productService.deleteById(Id);
        return "redirect:/mainscreen";
    }

    public ProductController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/associatepart")
    public String associatePart(@Valid @RequestParam("partID") int Id, Model model){
        ProductService productService = context.getBean(ProductServiceImpl.class);

        if (currentProduct == null || currentProduct.getId() == 0) { // Check if currentProduct is null (meaning it hasn't been saved yet)
            currentProduct = new Product();
        }

        currentProduct.getPart().add(partService.searchById(Id));
        partService.searchById(Id).getProducts().add(currentProduct);
        productService.save(currentProduct);
        partService.save(partService.searchById(Id));

        model.addAttribute("product", currentProduct);
        model.addAttribute("assparts",currentProduct.getPart());
        model.addAttribute("availparts", getAvailableParts());
        return "productForm";
    }

    @GetMapping("/removepart")
    public String removePart(@RequestParam("partID") int Id, Model model){
        Part part = partService.searchById(Id);
        currentProduct.getPart().remove(part);
        part.getProducts().remove(currentProduct);

        ProductService productService = context.getBean(ProductServiceImpl.class);
        productService.save(currentProduct);
        partService.save(part);

        model.addAttribute("product", currentProduct);
        model.addAttribute("assparts", currentProduct.getPart());
        model.addAttribute("availparts", getAvailableParts());
        return "productForm";
    }

    @GetMapping("/purchase")
    public String purchaseProduct(@RequestParam("productID") int Id) {
        ProductService productService = context.getBean(ProductServiceImpl.class);
        Product newProduct = productService.searchById(Id);

        newProduct.purchase();
        productService.save(newProduct);
        return "redirect:/mainscreen";
    }

}
