package com.example.onlinephoneshop.controller;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/public")
public class PublicController {
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	BrandService brandService;

	@Autowired
	ManufacturerService manufacturerService;

	@Autowired
	PhoneService phoneService;

	@Autowired
	AccessoryService accessoryService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	RatingService ratingService;

	@GetMapping("reset-password")
	public Boolean resetPassword(@RequestParam String username){
		return userService.resetPassword(username.trim());
	}

	@GetMapping("categories")
	public List<Category> getAllCategories(){
		return categoryService.getAllCategories();
	}

	@GetMapping("categories/{id}")
	public Optional<Category> retrieveCategory(@PathVariable String id) {
		return categoryService.findById(id);
	}

	@GetMapping("categories/search")
	public Optional<Category> getCategoryByName(@RequestParam String name) {
		return categoryService.findCategoryByName(name);
	}

	@GetMapping("brands")
	public List<Brand> getAllBrands(){
		return brandService.getAllBrands();
	}

	@GetMapping("brands/{id}")
	public Optional<Brand> retrieveBrand(@PathVariable String id) {
		return brandService.findById(id);
	}

	@GetMapping("brands/search")
	public Optional<Brand> getBrandByName(@RequestParam String name) {
		return brandService.findBrandByName(name);
	}

	@GetMapping("manufacturers")
	public List<Manufacturer> getAllManufacturers(){
		return manufacturerService.getAllManufacturers();
	}

	@GetMapping("manufacturers/{id}")
	public Optional<Manufacturer> retrieveManufacturer(@PathVariable Long id) {
		return manufacturerService.findById(id);
	}

	@GetMapping("manufacturers/search")
	public Optional<Manufacturer> getManufacturerByName(@RequestParam String name) {
		return manufacturerService.findManufacturerByName(name);
	}

	@GetMapping("products")
	public List<Phone> getAllProducts(){
		return phoneService.getAllProducts();
	}

	@GetMapping("products/{productId}")
	public Object getProductById(@PathVariable String productId) throws Throwable {
		Object object = phoneService.getProductById(productId).get();
		if(object instanceof Phone)
			return phoneService.convertEntityToDTO((Phone) object);
		else return accessoryService.convertEntityToDTO((Accessory) object);
	}

	@GetMapping("ratings/products/{id}")
	public List<Rating> getProductRatings(@PathVariable String id){
		return ratingService.getAllRatingByProductId(id);
	}

	public List<Object> convertToListDTO(List<Object> objectList){
		List<Object> dtoList = new ArrayList<>();
		for (Object object: objectList) {
			if(object instanceof Phone)
				object = phoneService.convertEntityToDTO((Phone) object);
			else
				object = accessoryService.convertEntityToDTO((Accessory) object);

			dtoList.add(object);
		}
		return dtoList;
	}

	@GetMapping("products/search")
	public List<Object> getProductByName(@RequestParam String productName) throws Throwable {
		List<Object> objectList = phoneService.getProductByName(productName);
		return convertToListDTO(objectList);
	}

	@GetMapping("products/embedded-search")
	public List<Object> getProductByNameIgnoreCaseContaining(@RequestParam String productName) throws Throwable {
		List<Object> objectList = phoneService.getProductByNameIgnoreCaseContaining(productName);
		return convertToListDTO(objectList);
	}

	@GetMapping("products/category/{id}")
	public List<Object> getProductByCategoryId(@PathVariable String id) throws Throwable {
		List<Object> objectList = phoneService.getProductByCategoryId(id);
		return convertToListDTO(objectList);
	}

	@GetMapping("products/top-view")
	public List<Object> getProductTopView() throws Throwable {
		List<Object> objectList = phoneService.getTop10MostView();
		return convertToListDTO(objectList);
	}

	@GetMapping("products/top-discount")
	public List<Object> getProductTopDiscount() throws Throwable {
		List<Object> objectList = phoneService.getTop10MostDiscount();
		return convertToListDTO(objectList);
	}

	@GetMapping("products/top-newest")
	public List<Object> getProductNewest() throws Throwable {
		List<Object> objectList = phoneService.getTop10Newest();
		return convertToListDTO(objectList);
	}

	@GetMapping("products/best-seller/limit/{num}")
	public List<Object> getProductBestSeller(@PathVariable Integer num) throws Throwable {
		List<Object> objectList = phoneService.getTop10BestSeller(0, num);
		return convertToListDTO(objectList);
	}

	@GetMapping("payments")
	public List<Payment> getAllPayments(){
		return paymentService.getAllPayments();
	}

	@GetMapping("payments/{id}")
	public Payment getAllPayments(@PathVariable Integer id){
		return paymentService.getById(id);
	}
}