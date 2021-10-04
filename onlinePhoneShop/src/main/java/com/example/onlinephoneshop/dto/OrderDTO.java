package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderId;
    @NotBlank
    private String orderAddress;
    private String description;
    private Date orderDate;
    private String customerId;
    private String customerFullName;
    private String status;

    public OrderDTO(String orderId, Date orderDate, String orderAddress, String description, String customerId
            , String customerFullName, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.description = description;
        this.customerId = customerId;
        this.customerFullName = customerFullName;
        this.status = status;
    }
}