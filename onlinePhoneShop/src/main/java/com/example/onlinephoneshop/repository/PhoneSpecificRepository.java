package com.example.onlinephoneshop.repository;

import com.example.onlinephoneshop.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneSpecificRepository extends JpaRepository<Phone, String> {
    Boolean existsByImeiNo(String imeiNo);
}
