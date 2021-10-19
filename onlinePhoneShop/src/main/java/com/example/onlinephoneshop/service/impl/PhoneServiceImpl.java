package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.*;
import com.example.onlinephoneshop.service.AccessoryService;
import com.example.onlinephoneshop.service.PhoneService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PhoneServiceImpl implements PhoneService {
    @Autowired
    PhoneRepository phoneRepository;

    @Autowired
    PhoneSpecificRepository phoneSpecificRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ManufacturerRepository manufacturerRepository;

    @Autowired
    AccessoryService accessoryService;

    @Override
    public List<Object> getAllProducts() {
        return phoneRepository.findAll();
    }

    @Override
    public Boolean existByImeiNo(String imeiNo) {
        return phoneSpecificRepository.existsByImeiNo(imeiNo);
    }

    @Override
    public Phone savePhone(PhoneDTO phoneDTO) {
        Phone phone = convertDTOToEntity(phoneDTO);
        return (Phone) phoneRepository.save(phone);
    }

    @Override
    public Phone updatePhone(PhoneDTO phoneDTO, String id) throws Throwable {
        Phone existingPhone = (Phone) getProductById(id).get();
        Phone updatePhone = convertDTOToEntity(phoneDTO);

        existingPhone.setProductName(updatePhone.getProductName());
        existingPhone.setDescription(updatePhone.getDescription());
        existingPhone.setDiscount(updatePhone.getDiscount());
        existingPhone.setAvailable(updatePhone.getAvailable());
        existingPhone.setImage(updatePhone.getImage());
        existingPhone.setQuantity(updatePhone.getQuantity());
        existingPhone.setUnitPrice(updatePhone.getUnitPrice());
        existingPhone.setSpecial(updatePhone.getSpecial());
        existingPhone.setViewCount(updatePhone.getViewCount());
        existingPhone.setLabel(updatePhone.getLabel());
        existingPhone.setWarranty(updatePhone.getWarranty());
        existingPhone.setCommonCoef(updatePhone.getCommonCoef());
        existingPhone.setGamingCoef(updatePhone.getGamingCoef());
        existingPhone.setEntertainCoef(updatePhone.getEntertainCoef());

        existingPhone.setImeiNo(updatePhone.getImeiNo());
        existingPhone.setModel(updatePhone.getModel());
        existingPhone.setRam(updatePhone.getRam());
        existingPhone.setBatteryPower(updatePhone.getBatteryPower());
        existingPhone.setInMemory(updatePhone.getInMemory());
        existingPhone.setTouchScreen(updatePhone.getTouchScreen());
        existingPhone.setWifi(updatePhone.getWifi());
        existingPhone.setBluetooth(updatePhone.getBluetooth());
        existingPhone.setClockSpeed(updatePhone.getClockSpeed());
        existingPhone.setN_cores(updatePhone.getN_cores());
        existingPhone.setN_sim(updatePhone.getN_sim());
        existingPhone.setPxHeight(updatePhone.getPxHeight());
        existingPhone.setPxWidth(updatePhone.getPxWidth());
        existingPhone.setScreenHeight(updatePhone.getScreenHeight());
        existingPhone.setScreenWidth(updatePhone.getScreenWidth());
        existingPhone.setRefreshRate(updatePhone.getRefreshRate());
        existingPhone.setFrontCam(updatePhone.getFrontCam());
        existingPhone.setSupport_3G(updatePhone.getSupport_3G());
        existingPhone.setSupport_4G(updatePhone.getSupport_4G());
        existingPhone.setSupport_5G(updatePhone.getSupport_5G());
        existingPhone.setOtherSpecification(updatePhone.getOtherSpecification());

        existingPhone.setCategory(updatePhone.getCategory());
        existingPhone.setBrand(updatePhone.getBrand());
        existingPhone.setManufacturer(updatePhone.getManufacturer());

        return (Phone) phoneRepository.save(existingPhone);
    }

    @Override
    public List<Object> getByListIds(List<String> ids) {
        return phoneRepository.findAllById(ids);
    }

    @Override
    public Optional<Object> getProductById(String id) throws Throwable {
        Object object =  phoneRepository.findById(id).get();
        Phone phone = null;
        Accessory accessory = null;
        if(object instanceof Phone){
            phone = (Phone) object;
            phone.setViewCount(phone.getViewCount()+1);
            phoneRepository.save(phone);
            return Optional.of(phone);
        }
        else{
            accessory = (Accessory) object;
            accessory.setViewCount(accessory.getViewCount()+1);
            phoneRepository.save(accessory);
            return Optional.of(accessory);
        }
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
    public void deleteProductById(String id) {
        phoneRepository.deleteById(id);
    }

    @Override
    public Phone addAccessories(Set<String> accessoryList, String phoneId) throws Throwable {
        Phone existingPhone = (Phone) getProductById(phoneId).get();
        Set<Accessory> accessories = new HashSet<>();
        for(String id: accessoryList){
            Accessory accessory = (Accessory) phoneRepository.findById(id).get();
            accessories.add(accessory);
        }

        existingPhone.setAccessories(accessories);
        return (Phone) phoneRepository.save(existingPhone);
    }

    @Override
    public Set<AccessoryDTO> getAllAccessoriesOfPhone(String phoneId) throws Throwable {
        Object existingPhone = getProductById(phoneId).get();
        Phone phone = null;
        if(existingPhone instanceof Phone){
            phone = (Phone) existingPhone;
        }
        if(phone != null)
            return phone.getAccessories().stream().map(accessoryService::convertEntityToDTO).collect(Collectors.toSet());
        else return new HashSet<>();
    }

    @Override
    public List<Object> getProductByName(String productName) {
        return phoneRepository.findByProductName(productName);
    }

    @Override
    public List<Object> getProductByNameIgnoreCaseContaining(String productName) {
        return phoneRepository.findByProductNameIgnoreCaseContaining(productName);
    }

    @Override
    public List<Object> getProductByCategoryId(String categoryId) {
        return phoneRepository.findProductByCategory_CategoryId(categoryId);
    }

    // Statistics
    @Override
    public List<Object> getTop10MostView() {
        return phoneRepository.findTop10ByOrderByViewCountDesc();
    }

    @Override
    public List<Object> getTop10MostDiscount() {
        return phoneRepository.findTop10ByOrderByDiscountDesc();
    }

    @Override
    public List<Object> getTop10BestSeller(int offset, int limit) {
        return phoneRepository.findTopBestSellerProduct(PageRequest.of(offset, limit));
    }

    @Override
    public List<Object> getTop10Newest() {
        return phoneRepository.findTop10ByOrderByCreatedDateDesc();
    }
}
