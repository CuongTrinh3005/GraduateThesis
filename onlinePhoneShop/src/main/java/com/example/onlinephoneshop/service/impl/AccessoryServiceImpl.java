package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.repository.*;
import com.example.onlinephoneshop.service.AccessoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccessoryServiceImpl implements AccessoryService {
    @Autowired
    AccessoryRepository accessoryRepository;

    @Autowired
    AccessorySpecificRepository accessorySpecificRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Accessory> getAllAccessories() {
        return accessorySpecificRepository.findAll();
    }

    @Override
    public Accessory saveAccessory(AccessoryDTO accessoryDTO) {
        Accessory accessory = convertDTOToEntity(accessoryDTO);
        return (Accessory) accessoryRepository.save(accessory);
    }

    @Override
    public void deleteAccessoryById(String id) {
        accessoryRepository.deleteById(id);
    }

    @Override
    public Accessory convertDTOToEntity(AccessoryDTO accessoryDTO) {
        Accessory accessory = modelMapper.map(accessoryDTO, Accessory.class);
        Brand brand = brandRepository.findByBrandName(accessoryDTO.getBrandName());
        accessory.setBrand(brand);

        Category category = categoryRepository.findByCategoryName(accessoryDTO.getCategoryName());
        accessory.setCategory(category);

        Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(accessoryDTO.getManufacturerName());
        accessory.setManufacturer(manufacturer);

        return accessory;
    }

    @Override
    public AccessoryDTO convertEntityToDTO(Accessory accessory) {
        AccessoryDTO accessoryDTO = modelMapper.map(accessory, AccessoryDTO.class);
        accessoryDTO.setCategoryName(accessory.getCategory().getCategoryName());
        accessoryDTO.setBrandName(accessory.getBrand().getBrandName());
        accessoryDTO.setManufacturerName(accessory.getManufacturer().getManufacturerName());

        return accessoryDTO;
    }

    @Override
    public Accessory updateAccessory(AccessoryDTO accessoryDTO, String id) throws Throwable {
        Accessory existingAccessory = getAccessoryById(id).get();
        Accessory updateAccessory = convertDTOToEntity(accessoryDTO);

        existingAccessory.setProductName(updateAccessory.getProductName());
        existingAccessory.setDescription(updateAccessory.getDescription());
        existingAccessory.setSpecification(updateAccessory.getSpecification());
        existingAccessory.setDiscount(updateAccessory.getDiscount());
        existingAccessory.setAvailable(updateAccessory.getAvailable());
        existingAccessory.setImage(updateAccessory.getImage());
        existingAccessory.setQuantity(updateAccessory.getQuantity());
        existingAccessory.setUnitPrice(updateAccessory.getUnitPrice());
        existingAccessory.setSpecial(updateAccessory.getSpecial());
        existingAccessory.setViewCount(updateAccessory.getViewCount());
        existingAccessory.setLabel(updateAccessory.getLabel());
        existingAccessory.setWarranty(updateAccessory.getWarranty());
        existingAccessory.setCommonCoef(updateAccessory.getCommonCoef());
        existingAccessory.setGamingCoef(updateAccessory.getGamingCoef());
        existingAccessory.setEntertainCoef(updateAccessory.getEntertainCoef());

        existingAccessory.setFunctions(updateAccessory.getFunctions());
        existingAccessory.setCompatibleDevices(updateAccessory.getCompatibleDevices());

        existingAccessory.setCategory(updateAccessory.getCategory());
        existingAccessory.setBrand(updateAccessory.getBrand());
        existingAccessory.setManufacturer(updateAccessory.getManufacturer());

        return (Accessory) accessoryRepository.save(existingAccessory);
    }

    @Override
    public Optional<Accessory> getAccessoryById(String id) throws Throwable {
        return accessoryRepository.findById(id);
    }
}
