package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.service.AccessoryService;
import com.example.onlinephoneshop.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/admin/products")
public class AdminProductController {
    @Autowired
    PhoneService phoneService;

    @Autowired
    AccessoryService accessoryService;

    @PostMapping("phones")
    public ResponseEntity<?> savePhone(@Valid @RequestBody PhoneDTO phone){
        phoneService.savePhone(phone);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(phone.getProductId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("phones/{phoneId}")
    void deletePhoneById(@PathVariable String phoneId) throws Throwable {
        Phone phone=null;
        Optional<Object> phoneOtp = phoneService.getProductById(phoneId);
        if(phoneOtp.get() instanceof Phone){
            phone = (Phone) phoneOtp.get();
        }
        else return;

        if(phone.getOrderDetails().size()==0 && phone.getRatings().size()==0)
            phoneService.deletePhoneById(phoneId);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_PRODUCT.getDescription());
    }

    @PutMapping("phones/{phoneId}")
    @Transactional
    public ResponseEntity<Phone> updatePhone(@Valid @RequestBody PhoneDTO phoneDTO, @PathVariable String phoneId) throws Throwable {
        return new ResponseEntity<Phone>(phoneService.updatePhone(phoneDTO, phoneId), HttpStatus.OK);
    }

    @PostMapping("accessories")
    public ResponseEntity<?> saveAccessory(@Valid @RequestBody AccessoryDTO accessoryDTO){
        accessoryService.saveAccessory(accessoryDTO);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(accessoryDTO.getProductId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("accessories/{accessoryId}")
    void deleteAccessoryById(@PathVariable String accessoryId) throws Throwable {
        Optional<Accessory> accessoryOtp = accessoryService.getAccessoryById(accessoryId);
        Accessory accessory = accessoryOtp.get();

        if(accessory.getOrderDetails().size()==0 && accessory.getRatings().size()==0)
            accessoryService.deleteAccessoryById(accessoryId);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_PRODUCT.getDescription());
    }

    @PutMapping("accessories/{accessoryId}")
    @Transactional
    public ResponseEntity<Accessory> updateAccessory(@Valid @RequestBody AccessoryDTO accessoryDTO, @PathVariable String accessoryId) throws Throwable {
        return new ResponseEntity<Accessory>(accessoryService.updateAccessory(accessoryDTO, accessoryId), HttpStatus.OK);
    }
}
