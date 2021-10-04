package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Category findByCategoryName(String categoryName);
    Boolean existsByCategoryId(String categoryId);
    Boolean existsByCategoryName(String categoryName);
}
