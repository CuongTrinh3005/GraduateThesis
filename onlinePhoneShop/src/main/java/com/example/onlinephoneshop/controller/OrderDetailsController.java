package com.example.onlinephoneshop.controller;

import java.util.ArrayList;
import java.util.List;

import com.example.onlinephoneshop.dto.OrderDetailsDTO;
import com.example.onlinephoneshop.entity.OrderDetails;
import com.example.onlinephoneshop.entity.OrderDetails.OrderDetailID;
import com.example.onlinephoneshop.service.OrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class OrderDetailsController {
	@Autowired
	OrderDetailsService orderDetailService;

	@GetMapping("order_details/{orderid}")
	public List<OrderDetailsDTO> getOrderDetail(@PathVariable String orderid){
		return orderDetailService.getAllOrderDetailByOrderId(orderid);
	}
}
