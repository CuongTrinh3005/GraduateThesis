package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.OrderDTO;
import com.example.onlinephoneshop.entity.Order;
import com.example.onlinephoneshop.entity.OrderDetails;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface OrderService {
	List<Order> findOrderByUserId(String userId);
	List<Order> findOrderByOrderDateDesc();
	Optional<Order> findOrderById(String id);
	Order saveOrder(Order order);
	Order updateOrder(OrderDTO order, String id);
	String composeConfirmOrder(List<OrderDetails> details, Date date, float totalCost);
	OrderDTO convertToDTO(Order order);
}