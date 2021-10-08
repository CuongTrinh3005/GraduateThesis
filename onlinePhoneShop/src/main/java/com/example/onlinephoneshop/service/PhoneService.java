package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface PhoneService {
    List<Object> getAllProducts();
    Phone savePhone(PhoneDTO phoneDTO);
    void deleteProductById(String id);
    Phone convertDTOToEntity(PhoneDTO phoneDTO);
    PhoneDTO convertEntityToDTO(Phone phone);
    Phone updatePhone(PhoneDTO phoneDTO, String id) throws Throwable;
    Optional<Object> getProductById(String id) throws Throwable;
    Phone addAccessories(Set<String> accessoryList, String phoneId) throws Throwable;
    List<Object> getProductByName(String productName);
    List<Object> getProductByNameIgnoreCaseContaining(String productName);
    List<Object> getProductByCategoryId(String categoryId);
    List<Object> getTop10MostView();
    List<Object> getTop10MostDiscount();
    List<Object> getTop10BestSeller(int offset, int limit);
    List<Object> getTop10Newest();
    Boolean existByImeiNo(String imeiNo);
    Set<AccessoryDTO> getAllAccessoriesOfPhone(String phoneId) throws Throwable;
}
