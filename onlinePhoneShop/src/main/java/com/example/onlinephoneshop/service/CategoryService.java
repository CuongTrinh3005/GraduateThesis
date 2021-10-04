package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.entity.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<Category> getAllCategories();
    Category saveCategory(Category category);
    Optional<Category> findCategoryByName(String name);
    Optional<Category> findById(String id);
    Boolean existsById(String id);
    void delete(Category category);
    Category updateCategory(Category category, String id);
    Boolean existByName(String name);
}
