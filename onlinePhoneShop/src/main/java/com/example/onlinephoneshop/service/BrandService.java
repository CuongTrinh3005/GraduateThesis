package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.entity.Brand;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BrandService {
    List<Brand> getAllBrands();
    Brand saveBrand(Brand brand);
    Optional<Brand> findBrandByName(String name);
    Optional<Brand> findById(String id);
    Boolean existsById(String id);
    void delete(Brand brand);
    Brand updateBrand(Brand brand, String id);
    Boolean existByName(String name);
}
