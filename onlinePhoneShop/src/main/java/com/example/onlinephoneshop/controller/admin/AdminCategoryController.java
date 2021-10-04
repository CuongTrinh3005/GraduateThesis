package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.entity.Category;
import com.example.onlinephoneshop.entity.User;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceAlreadyExistedException;
import com.example.onlinephoneshop.service.CategoryService;
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
@RequestMapping("api/admin/categories")
public class AdminCategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> insertCategory(@Valid @RequestBody Category category){
        Boolean existed = categoryService.existsById(category.getCategoryId());
        if(existed) throw new ResourceAlreadyExistedException(CustomMessages.CATEGORY_ID_EXISTED.getDescription());

        Boolean existedName = categoryService.existByName(category.getCategoryName());
        if(existedName) throw new ResourceAlreadyExistedException(CustomMessages.CATEGORY_NAME_EXISTED.getDescription());

        categoryService.saveCategory(category);

        //Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getCategoryId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category, @PathVariable String id) {
        return new ResponseEntity<Category>(categoryService.updateCategory(category, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable String id) {
        Optional<Category> categoryOpt = categoryService.findById(id);

        Category category = categoryOpt.get();
        if (category.getProducts().size() == 0)
            categoryService.delete(category);
        else
            throw new CustomException(CustomMessages.NOT_DELETE_CATEGORY.getDescription());
    }
}
