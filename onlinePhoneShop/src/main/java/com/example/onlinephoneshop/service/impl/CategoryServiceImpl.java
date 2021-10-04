package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.entity.Category;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.CategoryRepository;
import com.example.onlinephoneshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> findCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name);
        if (category == null)
            throw new ResourceNotFoundException(CustomMessages.CATEGORY_NOT_FOUND.getDescription());

        return Optional.of(category);
    }

    @Override
    public Optional<Category> findById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(CustomMessages.CATEGORY_NOT_FOUND.getDescription(), id)));
        return Optional.of(category);
    }

    @Override
    public Boolean existsById(String id) {
        return categoryRepository.existsByCategoryId(id);
    }

    @Override
    public Boolean existByName(String name) {
        return categoryRepository.existsByCategoryName(name);
    }

    @Override
    public void delete(Category category) {
        categoryRepository.delete(category);
    }

    @Override
    public Category updateCategory(Category category, String id) {
        Optional<Category> categoryOpt = findById(id);

        Category existedCategory = categoryOpt.get();

        existedCategory.setCategoryId(category.getCategoryId());
        existedCategory.setCategoryName(category.getCategoryName());
        existedCategory.setDescription(category.getDescription());
        existedCategory.setProducts(category.getProducts());

        return saveCategory(existedCategory);
    }
}
