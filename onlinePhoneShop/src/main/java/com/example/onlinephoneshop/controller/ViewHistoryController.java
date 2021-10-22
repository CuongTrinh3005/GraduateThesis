package com.example.onlinephoneshop.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.entity.Accessory;
import com.example.onlinephoneshop.entity.Phone;
import com.example.onlinephoneshop.entity.Product;
import com.example.onlinephoneshop.entity.ViewHistory;
import com.example.onlinephoneshop.entity.ViewHistory.ViewHistoryId;
import com.example.onlinephoneshop.service.AccessoryService;
import com.example.onlinephoneshop.service.PhoneService;
import com.example.onlinephoneshop.service.ViewHistoryService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/viewing-histories")
public class ViewHistoryController {
	@Autowired
	ViewHistoryService viewHistoryService;
	
	@Autowired
	PhoneService phoneService;
	
	@Autowired
	AccessoryService accessoryService;
	
	@GetMapping("{userid}")
	public List<Object> getAllProductsOfUserViewing(@PathVariable String userid){
		List<Product> products = viewHistoryService.getAllProductsFromUserHistoryViewing(userid);
				
		List<Object> productDTOList = new ArrayList<>();
		Phone phone = null; Accessory accessory = null;
		for (Object product: products) {
			if(product instanceof Phone){
				phone = (Phone) product;
				PhoneDTO phoneDTO = phoneService.convertEntityToDTO(phone);
				productDTOList.add(phoneDTO);
			}
			else{
				accessory = (Accessory) product;
				AccessoryDTO accessoryDTO = accessoryService.convertEntityToDTO(accessory);
				productDTOList.add(accessoryDTO);
			}
		}
		return productDTOList;
	}
	
	@GetMapping("date-descending/{userid}")
	public List<Object> getAllProductsOfUserViewingDateDescending(@PathVariable String userid){
		List<Product> products = viewHistoryService.getAllProductsFromUserHistoryViewingDateDescending(userid);
				
		List<Object> productDTOList = new ArrayList<>();
		Phone phone = null; Accessory accessory = null;
		for (Object product: products) {
			if(product instanceof Phone){
				phone = (Phone) product;
				PhoneDTO phoneDTO = phoneService.convertEntityToDTO(phone);
				productDTOList.add(phoneDTO);
			}
			else{
				accessory = (Accessory) product;
				AccessoryDTO accessoryDTO = accessoryService.convertEntityToDTO(accessory);
				productDTOList.add(accessoryDTO);
			}
		}
		return productDTOList;
	}

	@PostMapping
	public ViewHistory saveHistory(@Valid @RequestBody ViewHistory history){
		return viewHistoryService.saveViewHistory(history);
	}
	
	@PutMapping()
	public ViewHistory increaseViewCount(@RequestParam String userId, @RequestParam String productId){
		ViewHistoryId id = new ViewHistoryId(userId, productId);
		return viewHistoryService.increaseViewCountInHistory(id);
	}
	
	@DeleteMapping("{userid}")
	public void deleteUserViewingHistory(@PathVariable String userid){
		viewHistoryService.deleteUserViewingHistory(userid);
	}
	
	@GetMapping("check-exist")
	Boolean checkUserViewingHistoryExisted(@RequestParam String userId, @RequestParam String productId){
		ViewHistoryId id = new ViewHistoryId(userId, productId);
		return viewHistoryService.isUserViewingHistoryExisted(id);
	}
}