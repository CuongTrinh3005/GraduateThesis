package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AccessoryService {
    List<Accessory> getAllAccessories();
    Accessory saveAccessory(AccessoryDTO accessoryDTO);
    void deleteAccessoryById(String id);
    Accessory convertDTOToEntity(AccessoryDTO accessoryDTO);
    AccessoryDTO convertEntityToDTO(Accessory accessory);
    Accessory updateAccessory(AccessoryDTO accessoryDTO, String id) throws Throwable;
    Optional<Accessory> getAccessoryById(String id) throws Throwable;
}
