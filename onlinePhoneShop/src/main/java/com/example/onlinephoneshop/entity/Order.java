package com.example.onlinephoneshop.entity;

import com.example.onlinephoneshop.enums.OrderStatus;
import com.example.onlinephoneshop.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import java.util.Collection;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends AuditModel {
    @Id
    @Column
    @Length(max = 15)
    @GeneratedValue(generator = "userid-generator")
    @GenericGenerator(name = "userid-generator",
            parameters = @Parameter(name = "prefix", value = "OD"),
            strategy = "com.example.onlinephoneshop.generators.UserIdGenerator")
    private String orderId;

    @Column
    @NotBlank
    private String orderAddress;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private OrderStatus state = OrderStatus.CREATED;

    // Specify order relationships
    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private Collection<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "paymentId")
    @JsonIgnore
    private Payment payment;
}
