package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.entity.Manufacturer;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceAlreadyExistedException;
import com.example.onlinephoneshop.service.ManufacturerService;
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
@RequestMapping("api/admin/manufacturers")
public class AdminManufacturerController {
    @Autowired
    ManufacturerService manufacturerService;

    @PostMapping
    public ResponseEntity<?> insertBrand(@Valid @RequestBody Manufacturer manufacturer){
        Boolean existed = manufacturerService.existsById(manufacturer.getManufacturerId());
        if(existed) throw new ResourceAlreadyExistedException(CustomMessages.MANUFACTURER_NOT_FOUND.getDescription());

        Boolean existedName = manufacturerService.existByName(manufacturer.getManufacturerName());
        if(existedName) throw new ResourceAlreadyExistedException(CustomMessages.MANUFACTURER_NAME_EXISTED.getDescription());

        manufacturerService.saveManufacturer(manufacturer);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(manufacturer.getManufacturerId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Manufacturer> updateManufacturer(@Valid @RequestBody Manufacturer manufacturer, @PathVariable Long id) {
        return new ResponseEntity<Manufacturer>(manufacturerService.updateManufacturer(manufacturer, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturer(@PathVariable Long id) {
        Optional<Manufacturer> manufacturerOpt = manufacturerService.findById(id);

        Manufacturer manufacturer = manufacturerOpt.get();
        if (manufacturer.getProducts().size() == 0)
            manufacturerService.delete(manufacturer);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_MANUFACTURER.getDescription());
    }
}
