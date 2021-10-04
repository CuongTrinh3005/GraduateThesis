package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.BrandRepository;
import com.example.onlinephoneshop.repository.CategoryRepository;
import com.example.onlinephoneshop.repository.ManufacturerRepository;
import com.example.onlinephoneshop.repository.PhoneRepository;
import com.example.onlinephoneshop.service.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Override
    public List<Phone> getAllProducts() {
        return phoneRepository.findAll();
    }

    @Override
    public Phone savePhone(PhoneDTO phoneDTO) {
        Phone phone = convertDTOToEntity(phoneDTO);
        return (Phone) phoneRepository.save(phone);
    }

    @Override
    public Phone updatePhone(PhoneDTO phoneDTO, String id) throws Throwable {
        Phone existingPhone = getPhoneById(id).get();
        Phone updatePhone = convertDTOToEntity(phoneDTO);

        existingPhone.setProductName(updatePhone.getProductName());
        existingPhone.setDescription(updatePhone.getDescription());
        existingPhone.setDiscount(updatePhone.getDiscount());
        existingPhone.setAvailable(updatePhone.getAvailable());
        existingPhone.setImage(updatePhone.getImage());

        existingPhone.setQuantity(updatePhone.getQuantity());
        existingPhone.setUnitPrice(updatePhone.getUnitPrice());
        existingPhone.setSpecial(updatePhone.getSpecial());
        existingPhone.setSpecification(updatePhone.getSpecification());
        existingPhone.setViewCount(updatePhone.getViewCount());
        existingPhone.setLabel(updatePhone.getLabel());
        existingPhone.setWarranty(updatePhone.getWarranty());

        existingPhone.setImeiNo(updatePhone.getImeiNo());
        existingPhone.setModel(updatePhone.getModel());

        existingPhone.setCategory(updatePhone.getCategory());
        existingPhone.setBrand(updatePhone.getBrand());
        existingPhone.setManufacturer(updatePhone.getManufacturer());

        return (Phone) phoneRepository.save(existingPhone);
    }

    @Override
    public Optional<Phone> getPhoneById(String id) throws Throwable {
        Phone phone = (Phone) phoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CustomMessages.PRODUCT_NOT_FOUND.getDescription()));
        return Optional.of(phone);
    }

    @Override
    public Phone convertDTOToEntity(PhoneDTO phoneDTO) {
        Phone phone = modelMapper.map(phoneDTO, Phone.class);
        Brand brand = brandRepository.findByBrandName(phoneDTO.getBrandName());
        phone.setBrand(brand);

        Category category = categoryRepository.findByCategoryName(phoneDTO.getCategoryName());
        phone.setCategory(category);

        Manufacturer manufacturer = manufacturerRepository.findByManufacturerName(phoneDTO.getManufacturerName());
        phone.setManufacturer(manufacturer);

        return phone;
    }

    @Override
    public PhoneDTO convertEntityToDTO(Phone phone) {
        PhoneDTO phoneDTO = modelMapper.map(phone, PhoneDTO.class);
        phoneDTO.setCategoryName(phone.getCategory().getCategoryName());
        phoneDTO.setBrandName(phone.getBrand().getBrandName());
        phoneDTO.setManufacturerName(phone.getManufacturer().getManufacturerName());

        return phoneDTO;
    }

    @Override
    public void deletePhoneById(String id) {
        phoneRepository.deleteById(id);
    }
}
