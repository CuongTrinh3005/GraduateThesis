package com.example.onlinephoneshop.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.onlinephoneshop.dto.OrderDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.enums.OrderStatus;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.AccessoryRepository;
import com.example.onlinephoneshop.repository.OrderRepository;
import com.example.onlinephoneshop.repository.PhoneRepository;
import com.example.onlinephoneshop.repository.ProductRepository;
import com.example.onlinephoneshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	PhoneRepository phoneRepository;

	@Autowired
	AccessoryRepository accessoryRepository;

	@Override
	public List<Order> findOrderByUserId(String userId) {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
		return orderRepository.findOrderByUser_UserId(userId, sort);
	}

	@Override
	public Optional<Order> findOrderById(String id) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Order " + id + " not found"));
		return Optional.of(order);
	}

	@Override
	public Order saveOrder(Order order) {
		return orderRepository.save(order);
	}

	@Override
	public Order updateOrder(OrderDTO order, String id) {
		Optional<Order> orderOpt = findOrderById(id);

		Order existedOrder = orderOpt.get();
		existedOrder.setOrderAddress(order.getOrderAddress());
		existedOrder.setDescription(order.getDescription());

		OrderStatus status= OrderStatus.getByState(order.getStatus());
		existedOrder.setState(status);
		
		return saveOrder(existedOrder);
	}

	@Override
	public List<Order> findOrderByOrderDateDesc() {
		return orderRepository.findAllByOrderByCreatedDateDesc();
	}

	@Override
	public String composeConfirmOrder(List<OrderDetails> details, Date date, float totalCost) {
		String body = "You have an order on " + date.toString() + " with details: \r\n";
		body += "\r\n";
		body += "Mã đơn đặt hàng: " + details.get(0).getOrderDetailID().getOrderId() + "\r\n";
		body += "\r\n";
		Phone phone = null;
		Accessory accessory = null;
		String productName = "";
		for (OrderDetails detail : details) {
			body += "Mã sản phẩm: " + detail.getOrderDetailID().getProductId() + "\r\n";
			body += "\r\n";
			Object product = phoneRepository.getById(detail.getOrderDetailID().getProductId());
			if(product instanceof Phone){
				phone = (Phone) product;
				productName = phone.getProductName();
			}
			else{
				accessory = (Accessory) product;
				productName = accessory.getProductName();
			}
			body += "Tên sản phẩm: " + productName + "\r\n";
			body += "\r\n";
			body += "Số lượng: " + detail.getQuantityOrder() + "\r\n";
			body += "\r\n";
			body += "Đơn giá: " + detail.getUnitPrice() + " VNĐ \r\n";
			body += "\r\n";
			if(detail.getDiscount() > 0){
				body += "Giảm giá: " + detail.getDiscount()*100 + "%" + "\r\n";
				body += "\r\n";
			}
			body += "----------------------------------------------------------- \r\n";
			body += "\r\n";
		}
		body += "Tổng thành tiền: " + totalCost + " VNĐ \r\n";
		body += "\r\n";
		body += "Hân hạnh phục vụ quý khách !";
		return body;
	}

	@Override
	public OrderDTO convertToDTO(Order order) {
		return new OrderDTO(order.getOrderId(), order.getCreatedDate(),order.getOrderAddress()
				, order.getDescription(), order.getUser().getUserId()
				, order.getUser().getFullName(), order.getState().name());
	}
}