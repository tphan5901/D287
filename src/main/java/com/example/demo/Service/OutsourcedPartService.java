package com.example.demo.Service;

import com.example.demo.DataObjects.OutsourcedPart;

import java.util.List;


public interface OutsourcedPartService {
        public List<OutsourcedPart> findAll();
        public OutsourcedPart findById(int Id);
        public void save (OutsourcedPart part);
        public void deleteById(int Id);
        public Long getNextId();
}
