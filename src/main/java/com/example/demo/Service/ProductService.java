package com.example.demo.Service;
import com.example.demo.DataObjects.Product;
import java.util.List;


public interface ProductService {
    public List<Product> getAll();
    public Product searchById(int Id);
    public void save (Product product);
    public void deleteById(int Id);
    public List<Product> searchProduct(String keyword);

}
