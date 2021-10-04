package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ProductRepository<T extends Product> extends JpaRepository<T, String> {
//    List<T> findByProductName(String productName);
//    List<T> findProductByBrand_BrandName(String brandName);
//    List<T> findProductByManufacturer_ManufacturerName(String manufacturerName);
//    List<T> findProductByCategory_CategoryName(String categoryName);
//    List<T> findProductByCategory_CategoryId(String categoryId);
//    List<T> findTop10ByOrderByCreatedDateDesc();
//    List<T> findTop10ByOrderByDiscountDesc();
//    List<T> findTop10ByOrderByViewCountDesc();
//    List<T> findAllByOrderByProductIdAsc();
//    @Query("FROM products p ORDER BY size(p.orderDetails) DESC")
//    List<T> findTopBestSellerProduct(Pageable pageable);
//    List<T> findByProductNameOrCategory_CategoryName(String bookName, String categoryName);
//    List<T> findByProductNameIgnoreCaseContaining(String bookName);

    List<T> findAll();
//    Optional<T> findById(String id);
//    Optional<T> findById(ID id);


    //    Iterable<T> findAll(Sort sort);
//    Page<T> findAll(Pageable pageable);

//	@Query("SELECT b FROM Book b WHERE b.bookName LIKE %:info% or b.category.categoryName LIKE %:info%")
//	List<Book> searchByNameOrGenreLike(@Param("info") String info);
}
