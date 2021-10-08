package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessorySpecificRepository extends JpaRepository<Accessory, String> {
}
