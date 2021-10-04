package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.entity.Manufacturer;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.ManufacturerRepository;
import com.example.onlinephoneshop.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Override
    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepository.findAll();
    }

    @Override
    public Manufacturer saveManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Optional<Manufacturer> findManufacturerByName(String name) {
        Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(name);
        if (manufacturer == null)
            throw new ResourceNotFoundException(CustomMessages.BRAND_NOT_FOUND.getDescription());

        return Optional.of(manufacturer);
    }

    @Override
    public Optional<Manufacturer> findById(Long id) {
        Manufacturer manufacturer = manufacturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CustomMessages.MANUFACTURER_NOT_FOUND.getDescription()));
        return Optional.of(manufacturer);
    }

    @Override
    public Boolean existsById(Long id) {
        return manufacturerRepository.existsByManufacturerId(id);
    }

    @Override
    public Boolean existByName(String name) {
        return manufacturerRepository.existsByManufacturerName(name);
    }

    @Override
    public void delete(Manufacturer brand) {
        manufacturerRepository.delete(brand);
    }

    @Override
    public Manufacturer updateManufacturer(Manufacturer manufacturer, Long id) {
        Optional<Manufacturer> manufacturerOpt = findById(id);
        Manufacturer existedManufacturer = manufacturerOpt.get();

        existedManufacturer.setManufacturerId(manufacturer.getManufacturerId());
        existedManufacturer.setManufacturerName(manufacturer.getManufacturerName());
        existedManufacturer.setEmail(manufacturer.getEmail());
        existedManufacturer.setAddress(manufacturer.getAddress());
        existedManufacturer.setPhoneNumber(manufacturer.getPhoneNumber());
        existedManufacturer.setCountry(manufacturer.getCountry());

        existedManufacturer.setProducts(manufacturer.getProducts());

        return saveManufacturer(existedManufacturer);
    }
}
