package com.example.demo.service;

import com.example.demo.domain.InhousePart;
import com.example.demo.domain.OutsourcedPart;
import com.example.demo.domain.Part;

import java.util.List;


public interface InhousePartService {
    public List<InhousePart> findAll();
    public InhousePart findById(int Id);
    public void save (InhousePart part);
    public void deleteById(int Id);
    public Long getNextId();
}
