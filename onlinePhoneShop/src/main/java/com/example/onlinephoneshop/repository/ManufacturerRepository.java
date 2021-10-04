package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    Manufacturer findByManufacturerName(String manufacturerName);
    Boolean existsByManufacturerId(Long brandId);
    Boolean existsByManufacturerName(String manufacturerName);
}
