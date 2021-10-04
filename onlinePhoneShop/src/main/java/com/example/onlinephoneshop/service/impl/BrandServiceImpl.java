package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.entity.Brand;
import com.example.onlinephoneshop.entity.Category;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.BrandRepository;
import com.example.onlinephoneshop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    BrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> findBrandByName(String name) {
        Brand brand = brandRepository.findByBrandName(name);
        if (brand == null)
            throw new ResourceNotFoundException(CustomMessages.BRAND_NOT_FOUND.getDescription());

        return Optional.of(brand);
    }

    @Override
    public Optional<Brand> findById(String id) {
        Brand category = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CustomMessages.BRAND_NOT_FOUND.getDescription()));
        return Optional.of(category);
    }

    @Override
    public Boolean existsById(String id) {
        return brandRepository.existsByBrandId(id);
    }

    @Override
    public Boolean existByName(String name) {
        return brandRepository.existsByBrandName(name);
    }

    @Override
    public void delete(Brand brand) {
        brandRepository.delete(brand);
    }

    @Override
    public Brand updateBrand(Brand brand, String id) {
        Optional<Brand> brandOpt = findById(id);
        Brand existedBrand = brandOpt.get();

        existedBrand.setBrandId(brand.getBrandId());
        existedBrand.setBrandName(brand.getBrandName());
        existedBrand.setDescription(brand.getDescription());
        existedBrand.setCountry(brand.getCountry());
        existedBrand.setProducts(brand.getProducts());

        return saveBrand(existedBrand);
    }
}
