package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.dto.OrderDTO;
import com.example.onlinephoneshop.entity.Order;
import com.example.onlinephoneshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/orders")
public class AdminOrderController {
    @Autowired
    OrderService orderService;

    @PutMapping("{id}")
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseEntity<Order> updateOrder(@Valid @RequestBody OrderDTO order, @PathVariable String id) {
        return new ResponseEntity<Order>(orderService.updateOrder(order, id), HttpStatus.OK);
    }
}
