package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.entity.Brand;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceAlreadyExistedException;
import com.example.onlinephoneshop.service.BrandService;
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
@RequestMapping("api/admin/brands")
public class AdminBrandController {
    @Autowired
    BrandService brandService;

    @PostMapping
    public ResponseEntity<?> insertBrand(@Valid @RequestBody Brand brand){
        Boolean existed = brandService.existsById(brand.getBrandId());
        if(existed) throw new ResourceAlreadyExistedException(CustomMessages.BRAND_ID_EXISTED.getDescription());

        Boolean existedName = brandService.existByName(brand.getBrandName());
        if(existedName) throw new ResourceAlreadyExistedException(CustomMessages.BRAND_NAME_EXISTED.getDescription());

        brandService.saveBrand(brand);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(brand.getBrandId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Brand> updateBrand(@Valid @RequestBody Brand brand, @PathVariable String id) {
        return new ResponseEntity<Brand>(brandService.updateBrand(brand, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteBrand(@PathVariable String id) {
        Optional<Brand> brandOpt = brandService.findById(id);

        Brand brand = brandOpt.get();
        if (brand.getProducts().size() == 0)
            brandService.delete(brand);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_BRAND.getDescription());
    }
}
