package com.example.onlinephoneshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })
@EnableJpaAuditing
public class OnlinePhoneShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlinePhoneShopApplication.class, args);
        System.out.println("This is my graduate thesis");
    }
}
