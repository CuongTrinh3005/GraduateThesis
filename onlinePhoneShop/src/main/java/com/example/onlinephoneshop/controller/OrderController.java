package com.example.onlinephoneshop.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.example.onlinephoneshop.dto.AccessoryDTO;
import com.example.onlinephoneshop.dto.OrderDTO;
import com.example.onlinephoneshop.dto.PhoneDTO;
import com.example.onlinephoneshop.dto.UserDTO;
import com.example.onlinephoneshop.entity.*;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class OrderController {
	@Autowired
	OrderService orderService;

	@Autowired
	UserService userService;

	@Autowired
	OrderDetailService orderDetailService;
	
	@Autowired
	PhoneService phoneService;

	@Autowired
	AccessoryService accessoryService;
	
	@Autowired
	EmailService emailService;

	@GetMapping("list/orders/{userId}")
	public List<Order> getAllOrdersByUsername(@PathVariable String userId) {
		return orderService.findOrderByUserId(userId);
	}
	
	@GetMapping("orders/date-descending")
	public List<OrderDTO> getAllOrderInDateDesceding() {
		return orderService.findOrderByOrderDateDesc().stream()
				.map(orderService::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("orders/{id}")
	public OrderDTO retrieveOrder(@PathVariable String id) {
		return orderService.convertToDTO(orderService.findOrderById(id).get()) ;
	}

	@PostMapping("orders")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ResponseEntity<Order> saveOrder(@Valid @RequestBody Order order, @RequestParam String username) {
		Order newOrder = new Order();
		try{
			UserDTO userDTO = userService.findByUsername(username);
			User user = userService.convertToEntity(userDTO);
			order.setUser(user);
			newOrder = orderService.saveOrder(order);

			// Save order details
			Collection<OrderDetails> details = order.getOrderDetails();
			List<OrderDetails> listDetails = new ArrayList<OrderDetails>();
			listDetails.addAll(details);
			
			for (OrderDetails detail : listDetails) {
				detail.getOrderDetailID().setOrderId(newOrder.getOrderId());
				
				Object object = (Object) phoneService.getProductById(detail.getOrderDetailID().getProductId()).get();
				Phone phone = null;
				Accessory accessory = null;
				if(object instanceof Phone){
					phone = (Phone) object;
					if(phone.getQuantity() < detail.getQuantityOrder())
						throw new CustomException("Not enough quantity");

					if(phone.getAvailable()==null || !phone.getAvailable())
						throw new CustomException("Book can not be sold");

					long quantityLeft = phone.getQuantity() - detail.getQuantityOrder();
					phone.setQuantity(quantityLeft);
					PhoneDTO phoneDTO = phoneService.convertEntityToDTO(phone);

					phoneService.updatePhone(phoneDTO, phoneDTO.getProductId());
				}
				else{
					accessory = (Accessory) object;
					if(accessory.getQuantity() < detail.getQuantityOrder())
						throw new CustomException("Not enough quantity");

					if(accessory.getAvailable()==null || !accessory.getAvailable())
						throw new CustomException("Book can not be sold");

					long quantityLeft = accessory.getQuantity() - detail.getQuantityOrder();
					accessory.setQuantity(quantityLeft);
					AccessoryDTO accessoryDTO = accessoryService.convertEntityToDTO(accessory);

					accessoryService.updateAccessory(accessoryDTO, accessoryDTO.getProductId());
				}

				orderDetailService.saveOrderDetail(detail);
			}
			
			// send confirm email
			Float totalPrice = orderDetailService.getTotalPrice(listDetails);
			String confirmBody = orderService.composeConfirmOrder(listDetails, new Date(), totalPrice);
			String to = user.getEmail();
			String subject = "Order confirmation";
			
			emailService.sendSimpleMessage(to, subject, confirmBody);
		}
		catch(Exception ex){
			ex.printStackTrace();
			System.err.println(ex.getMessage());
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newOrder.getOrderId()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("orders/{id}")
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public ResponseEntity<Order> updateOrder(@Valid @RequestBody OrderDTO order, @PathVariable String id) {
		return new ResponseEntity<Order>(orderService.updateOrder(order, id), HttpStatus.OK);
	}
}