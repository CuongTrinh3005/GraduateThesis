package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ProductRepository<T extends Product> extends JpaRepository<T, String> {
    List<T> findByProductName(String productName);
//    List<T> findProductByBrand_BrandName(String brandName);
//    List<T> findProductByManufacturer_ManufacturerName(String manufacturerName);
//    List<T> findProductByCategory_CategoryName(String categoryName);
    List<T> findProductByCategory_CategoryId(String categoryId);
    List<T> findTop10ByOrderByCreatedDateDesc();
    List<T> findTop10ByOrderByDiscountDesc();
    List<T> findTop10ByOrderByViewCountDesc();
    List<T> findAllByOrderByProductIdAsc();
    @Query("FROM Product p ORDER BY size(p.orderDetails) DESC")
    List<T> findTopBestSellerProduct(Pageable pageable);
//    List<T> findByProductNameOrCategory_CategoryName(String bookName, String categoryName);
    List<T> findByProductNameIgnoreCaseContaining(String bookName);
    List<T> findAll();
}
