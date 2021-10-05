package com.example.onlinephoneshop.service;

import com.example.onlinephoneshop.entity.Payment;

import java.util.List;

public interface PaymentService {
    List<Payment> getAllPayments();
    Payment getById(Integer id);
    Payment savePayment(Payment payment);
    Payment updatePayment(Payment payment, Integer id);
    void deletePayment(Integer id);
}
