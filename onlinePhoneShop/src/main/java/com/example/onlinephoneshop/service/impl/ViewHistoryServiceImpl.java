package com.example.onlinephoneshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.onlinephoneshop.entity.Product;
import com.example.onlinephoneshop.entity.ViewHistory;
import com.example.onlinephoneshop.entity.ViewHistory.ViewHistoryId;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.ViewHistoryRepository;
import com.example.onlinephoneshop.service.ViewHistoryService;

@Service
public class ViewHistoryServiceImpl implements ViewHistoryService{
	@Autowired
	ViewHistoryRepository viewHistoryRepository;
	
	@Override
	public List<Product> getAllProductsFromUserHistoryViewing(String userId) {
		// TODO Auto-generated method stub
		List<ViewHistory> histories = viewHistoryRepository.findByViewHistoryIdUserId(userId);
		List<Product> products = new ArrayList<Product>();
		
		for (ViewHistory history : histories) {
			products.add(history.getProduct());
		}
		
		return products;
	}

	@Override
	public List<Product> getAllProductsFromUserHistoryViewingDateDescending(String userId) {
		// TODO Auto-generated method stub
		List<ViewHistory> histories = viewHistoryRepository.findAllByOrderByUpdatedDateDesc();
		List<Product> products = new ArrayList<Product>();
		
		for (ViewHistory history : histories) {
			if(history.getUser().getUserId().equals(userId))
				products.add(history.getProduct());
		}
		
		return products;
	}

	@Override
	public ViewHistory saveViewHistory(ViewHistory historyViewing) {
		// TODO Auto-generated method stub
		return viewHistoryRepository.save(historyViewing);
	}

	@Override
	public ViewHistory increaseViewCountInHistory(ViewHistoryId historyViewingId) {
		// TODO Auto-generated method stub
		Optional<ViewHistory> existedHistoryOptional = viewHistoryRepository
				.findById(historyViewingId); 
		if(!existedHistoryOptional.isPresent())
			throw new ResourceNotFoundException(CustomMessages
					.HISTORY_VIEWING_NOT_EXISTED.getDescription());
		
		ViewHistory history = existedHistoryOptional.get();
		long currentViewCount = history.getViewCount();
		history.setViewCount(currentViewCount+1);
		
		return viewHistoryRepository.save(history);
	}

	@Override
	public void deleteUserViewingHistory(String userId) {
		// TODO Auto-generated method stub
		List<ViewHistory> histories = viewHistoryRepository
				.findByViewHistoryIdUserId(userId);
		
		for (ViewHistory viewHistory : histories) {
			viewHistoryRepository.delete(viewHistory);
		}
	}

	@Override
	public Boolean isUserViewingHistoryExisted(ViewHistoryId historyId) {
		// TODO Auto-generated method stub
		return viewHistoryRepository.existsByViewHistoryId(historyId);
	}

	@Override
	public Boolean existedByProductId(String productId) {
		List<ViewHistory> histories = viewHistoryRepository.findByViewHistoryIdProductId(productId);
		return (histories.size() > 0);
	}

	@Override
	public Boolean existedByUserId(String userId) {
		List<ViewHistory> histories = viewHistoryRepository.findByViewHistoryIdUserId(userId);
		return (histories.size() > 0);
	}
}
