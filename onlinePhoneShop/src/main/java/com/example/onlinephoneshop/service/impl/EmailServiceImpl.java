package com.example.onlinephoneshop.service.impl;

import com.example.onlinephoneshop.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {
	@Autowired
	JavaMailSender emailSender;
	
	public EmailServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void sendSimpleMessage(String to, String subject, String text) {
	   SimpleMailMessage message = new SimpleMailMessage(); 
	   message.setFrom("n17dccn017@student.ptithcm.edu.vn");
	   message.setTo(to); 
	   message.setSubject(subject); 
	   message.setText(text);
	   emailSender.send(message);
	}
}
