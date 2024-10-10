package com.example.demo.Repositories;
import com.example.demo.DataObjects.Part;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface PartRepository extends CrudRepository <Part,Long> {
    @Query("SELECT p FROM Part p WHERE p.name LIKE %:keyword%")
    List<Part> search(String keyword);
}
