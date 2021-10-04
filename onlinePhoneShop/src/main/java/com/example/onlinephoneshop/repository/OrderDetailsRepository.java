package com.example.onlinephoneshop.repository;

import java.util.List;

import com.example.onlinephoneshop.entity.OrderDetails;
import com.example.onlinephoneshop.entity.OrderDetails.OrderDetailID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailID> {
	List<OrderDetails> findByOrderDetailIDOrderId(String orderId);
}
