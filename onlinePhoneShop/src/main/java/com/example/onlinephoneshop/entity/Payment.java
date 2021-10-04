package com.example.onlinephoneshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment {
    @Id
    @Column
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column
    private String paymentType;
}
