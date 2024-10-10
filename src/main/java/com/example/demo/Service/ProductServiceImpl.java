package com.example.demo.Service;
import com.example.demo.DataObjects.Product;
import com.example.demo.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class  ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public List<Product> getAll() {
        List<Product> products = (List<Product>) productRepository.findAll();
        System.out.println("All Products: " + products);
        return products;
    }

    @Override
    public Product searchById(int Id) {
        Optional<Product> result = productRepository.findById((long) Id);
        Product newProduct = null;

        if (result.isPresent()) {
            newProduct = result.get();
        }
        return newProduct;
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(int Id) {
        productRepository.deleteById((long) Id);
    }

    public List<Product> searchProduct(String keyword) {
        if (keyword != null && !keyword.isEmpty()) {
            List<Product> products = productRepository.search(keyword);
            System.out.println("Products found: " + products.size());
            return products;
        }
        return (List<Product>) productRepository.findAll();
    }

}
