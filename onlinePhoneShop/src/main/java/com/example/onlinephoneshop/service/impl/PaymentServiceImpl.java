package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.entity.Payment;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.exception.ResourceNotFoundException;
import com.example.onlinephoneshop.repository.PaymentRepository;
import com.example.onlinephoneshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentRepository;

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment getById(Integer id) {
        Optional<Payment> paymentOptional =  paymentRepository.findById(id);
        if(!paymentOptional.isPresent())
            throw new ResourceNotFoundException(CustomMessages.PAYMENT_NOT_FOUND.getDescription());
        return paymentOptional.get();
    }

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment updatePayment(Payment payment, Integer id) {
        Payment existedPayment = getById(id);
        if(existedPayment != null){
            existedPayment.setPaymentType(payment.getPaymentType());
        }
        else throw new CustomException(CustomMessages.PAYMENT_NOT_FOUND.getDescription());

        return savePayment(existedPayment);
    }

    @Override
    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }
}
