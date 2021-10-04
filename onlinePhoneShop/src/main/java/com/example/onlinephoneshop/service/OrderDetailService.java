package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.dto.OrderDetailsDTO;
import com.example.onlinephoneshop.entity.OrderDetails;
import com.example.onlinephoneshop.entity.OrderDetails.OrderDetailID;
import java.util.List;
import java.util.Optional;

public interface OrderDetailService {
	Optional<OrderDetails> findById(OrderDetailID id);
	List<OrderDetails> findAllById(List<OrderDetailID> listOrderDetailId);
	OrderDetails saveOrderDetail(OrderDetails detail);
	List<OrderDetails> getAllOrderDetailByOrderId(String orderId);
	List<OrderDetailsDTO> convertToDTO(List<OrderDetails> orderDetails);
	Float getTotalPrice(List<OrderDetails> details);
}