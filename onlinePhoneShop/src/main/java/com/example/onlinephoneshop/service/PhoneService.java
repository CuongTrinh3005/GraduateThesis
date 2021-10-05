package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public interface PhoneService {
    List<Phone> getAllProducts();
    Phone savePhone(PhoneDTO phoneDTO);
    void deletePhoneById(String id);
    Phone convertDTOToEntity(PhoneDTO phoneDTO);
    PhoneDTO convertEntityToDTO(Phone phone);
    Phone updatePhone(PhoneDTO phoneDTO, String id) throws Throwable;
    Optional<Object> getProductById(String id) throws Throwable;
    Phone addAccessories(Set<String> accessoryList, String phoneId) throws Throwable;
}
