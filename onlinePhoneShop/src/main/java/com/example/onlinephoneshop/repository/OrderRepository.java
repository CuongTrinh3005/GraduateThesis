package com.example.onlinephoneshop.repository;

import java.util.List;

import com.example.onlinephoneshop.entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
	List<Order> findOrderByUser_UserId(String userId, Sort sort);
	List<Order> findAllByOrderByCreatedDateDesc();
}
