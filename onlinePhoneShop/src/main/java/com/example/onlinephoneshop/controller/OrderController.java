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
import com.example.onlinephoneshop.enums.CustomMessages;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/orders")
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
	PaymentService paymentService;
	
	@Autowired
	EmailService emailService;

	@GetMapping("list/{userId}")
	public List<Order> getAllOrdersByUsername(@PathVariable String userId) {
		return orderService.findOrderByUserId(userId);
	}
	
	@GetMapping("date-descending")
	public List<OrderDTO> getAllOrderInDateDesceding() {
		return orderService.findOrderByOrderDateDesc().stream()
				.map(orderService::convertToDTO).collect(Collectors.toList());
	}

	@GetMapping("{id}")
	public OrderDTO retrieveOrder(@PathVariable String id) {
		return orderService.convertToDTO(orderService.findOrderById(id).get()) ;
	}

	Boolean checkIfProductCanBeSold(List<OrderDetails> orderDetails) throws Throwable {
		try{
			for (OrderDetails detail : orderDetails) {
				Object object = (Object) phoneService.getProductById(detail.getOrderDetailID().getProductId()).get();
				Phone phone = null;
				Accessory accessory = null;
				if(object instanceof Phone){
					phone = (Phone) object;
					if(phone.getQuantity() < detail.getQuantityOrder())
						throw new CustomException(String.format(CustomMessages.NOT_ENOUGH_QUALITY_TO_SELL.getDescription()
								, phone.getProductId()));
					if(phone.getAvailable()==null || !phone.getAvailable())
						throw new CustomException(String.format(CustomMessages.NOT_AVAILABLE_TO_SELL.getDescription()
								, phone.getProductId()));
				}
				else{
					accessory = (Accessory) object;
					if(accessory.getQuantity() < detail.getQuantityOrder())
						throw new CustomException(String.format(CustomMessages.NOT_ENOUGH_QUALITY_TO_SELL.getDescription()
								, accessory.getProductId()));
					if(accessory.getAvailable()==null || !accessory.getAvailable())
						throw new CustomException(String.format(CustomMessages.NOT_AVAILABLE_TO_SELL.getDescription()
								, accessory.getProductId()));
				}
			}
		}
		catch (Exception ex){
			return false;
		}
		return true;
	}

	@PostMapping
	@Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = CustomException.class)
	public ResponseEntity<?> saveOrder(@Valid @RequestBody Order order, @RequestParam String username
			, @RequestParam Integer paymentId) {
		Order newOrder = new Order();
		try{
			if(!checkIfProductCanBeSold((List<OrderDetails>) order.getOrderDetails())){
				return new ResponseEntity<>(CustomMessages.ORDER_FAILED, HttpStatus.BAD_REQUEST);
			}
			UserDTO userDTO = userService.findByUsername(username);
			User user = userService.convertToEntity(userDTO);
			order.setUser(user);
			Payment payment = paymentService.getById(paymentId);
			order.setPayment(payment);

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
						throw new CustomException(String.format(CustomMessages.NOT_ENOUGH_QUALITY_TO_SELL.getDescription()
								, phone.getProductId()));

					if(phone.getAvailable()==null || !phone.getAvailable())
						throw new CustomException(String.format(CustomMessages.NOT_AVAILABLE_TO_SELL.getDescription()
								, phone.getProductId()));

					long quantityLeft = phone.getQuantity() - detail.getQuantityOrder();
					phone.setQuantity(quantityLeft);
					PhoneDTO phoneDTO = phoneService.convertEntityToDTO(phone);

					phoneService.updatePhone(phoneDTO, phoneDTO.getProductId());
				}
				else{
					accessory = (Accessory) object;
					if(accessory.getQuantity() < detail.getQuantityOrder())
						throw new CustomException(String.format(CustomMessages.NOT_ENOUGH_QUALITY_TO_SELL.getDescription()
								, accessory.getProductId()));

					if(accessory.getAvailable()==null || !accessory.getAvailable())
						throw new CustomException(String.format(CustomMessages.NOT_AVAILABLE_TO_SELL.getDescription()
								, accessory.getProductId()));

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
}