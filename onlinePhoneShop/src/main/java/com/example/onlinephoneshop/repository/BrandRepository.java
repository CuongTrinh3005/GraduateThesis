package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    Brand findByBrandName(String brandName);
    Boolean existsByBrandId(String brandId);
    Boolean existsByBrandName(String brandName);
}
