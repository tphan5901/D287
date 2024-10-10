package com.example.demo.Repositories;
import com.example.demo.DataObjects.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface ProductRepository extends CrudRepository<Product,Long> {
    @Query("SELECT item FROM Product item WHERE item.name LIKE %:keyword%")
    List<Product> search(String keyword);
}
