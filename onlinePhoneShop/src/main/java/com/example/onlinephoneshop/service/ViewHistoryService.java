package com.example.onlinephoneshop.service;

import java.util.List;

import com.example.onlinephoneshop.entity.Product;
import com.example.onlinephoneshop.entity.ViewHistory;
import com.example.onlinephoneshop.entity.ViewHistory.ViewHistoryId;

public interface ViewHistoryService {
	List<Product> getAllProductsFromUserHistoryViewing(String userId);
	List<Product> getAllProductsFromUserHistoryViewingDateDescending(String userId);
	ViewHistory saveViewHistory(ViewHistory historyViewing);
	ViewHistory increaseViewCountInHistory(ViewHistoryId historyViewingId);
	void deleteUserViewingHistory(String userId);
	Boolean isUserViewingHistoryExisted(ViewHistoryId historyId); 
}