package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDTO {
	private String orderId;
	private String productId;
	private String productName;
	private byte[] image;
	private Integer orderQuantity;
	private Float unitPrice;
	private Float discount;

	public OrderDetailsDTO(String orderId, String productId, Integer orderQuantity,
			Float unitPrice, Float discount) {
		super();
		this.orderId = orderId;
		this.productId = productId;
		this.orderQuantity = orderQuantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}
}