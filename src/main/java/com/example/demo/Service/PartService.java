package com.example.demo.Service;
import com.example.demo.DataObjects.Part;
import java.util.List;

public interface PartService {
    public List<Part> getAll();
    public Part searchById(int Id);
    public void save (Part part);
    public void deleteById(int Id);
    public List<Part> searchPart(String keyword);
}
