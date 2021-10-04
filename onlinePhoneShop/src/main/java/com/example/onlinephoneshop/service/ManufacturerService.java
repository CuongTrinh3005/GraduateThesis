package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.entity.Manufacturer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ManufacturerService {
    List<Manufacturer> getAllManufacturers();
    Manufacturer saveManufacturer(Manufacturer manufacturer);
    Optional<Manufacturer> findManufacturerByName(String name);
    Optional<Manufacturer> findById(Long id);
    Boolean existsById(Long id);
    void delete(Manufacturer brand);
    Manufacturer updateManufacturer(Manufacturer brand, Long id);
    Boolean existByName(String name);
}
