package com.example.onlinephoneshop.controller.admin;

import com.example.onlinephoneshop.entity.Payment;
import com.example.onlinephoneshop.enums.CustomMessages;
import com.example.onlinephoneshop.exception.CustomException;
import com.example.onlinephoneshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin/payments")
public class AdminPaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public Payment createPayment(@Valid @RequestBody Payment payment){
        if(payment == null) throw new CustomException(String.format(CustomMessages.DATA_INVALID_FORMAT.getDescription()
        , "payment"));

        return paymentService.savePayment(payment);
    }

    @PutMapping
    public Payment updatePayment(@Valid @RequestBody Payment payment, Integer paymentId){
        return paymentService.updatePayment(payment, paymentId);
    }

    @DeleteMapping("{id}")
    public void deletePayment(@PathVariable Integer id){
        paymentService.deletePayment(id);
    }
}
