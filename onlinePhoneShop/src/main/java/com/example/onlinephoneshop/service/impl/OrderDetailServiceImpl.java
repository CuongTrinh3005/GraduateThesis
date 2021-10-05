package com.example.onlinephoneshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.onlinephoneshop.dto.OrderDetailsDTO;
import com.example.onlinephoneshop.entity.OrderDetails;
import com.example.onlinephoneshop.entity.OrderDetails.OrderDetailID;
import com.example.onlinephoneshop.entity.Product;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.OrderDetailsRepository;
import com.example.onlinephoneshop.repository.PhoneRepository;
import com.example.onlinephoneshop.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	@Autowired
	OrderDetailsRepository orderDetailRepository;
	
	@Autowired
	PhoneRepository phoneRepository;

	@Override
	public Optional<OrderDetails> findById(OrderDetailID id) {
		OrderDetails orderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order detail id " + id + " not found"));
		return Optional.of(orderDetail);
	}

	@Override
	public List<OrderDetails> findAllById(List<OrderDetailID> listOrderDetailId) {
		return orderDetailRepository.findAllById(listOrderDetailId);
	}

	@Override
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = CustomException.class)
	public OrderDetails saveOrderDetail(OrderDetails detail) {
		if(detail.getDiscount() == null)
			detail.setDiscount(Float.valueOf(0));
		return orderDetailRepository.save(detail);
	}

	@Override
	public List<OrderDetails> getAllOrderDetailByOrderId(String orderId) {
		return orderDetailRepository.findByOrderDetailIDOrderId(orderId);
	}

	@Override
	public List<OrderDetailsDTO> convertToDTO(List<OrderDetails> orderDetails) {
		List<OrderDetailsDTO> listDto = new ArrayList<OrderDetailsDTO>();
		for (OrderDetails detail : orderDetails) {
			OrderDetailsDTO dto = new OrderDetailsDTO(detail.getOrder().getOrderId()
					, detail.getProduct().getProductId(), detail.getQuantityOrder()
					, detail.getUnitPrice(), detail.getDiscount());
			Product product = (Product) phoneRepository.findById(detail.getProduct().getProductId()).get();
			
			dto.setProductName(product.getProductName());
			dto.setImage(product.getImage());
			
			listDto.add(dto);
		}
		return listDto;
	}

	@Override
	public Float getTotalPrice(List<OrderDetails> details) {
		float totalOrder = 0;
		for (OrderDetails detail : details) {
			Float itemPrice = detail.getQuantityOrder() * detail.getUnitPrice() * (1-detail.getDiscount());
			totalOrder += itemPrice;
		}
		return totalOrder;
	}
}