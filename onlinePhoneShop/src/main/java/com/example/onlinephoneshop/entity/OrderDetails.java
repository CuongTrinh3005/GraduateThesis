package com.example.onlinephoneshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orderDetails")
public class OrderDetails {
    @EmbeddedId
    private OrderDetailID orderDetailID;

    @Column
    @NotNull
    @DecimalMin(value = "1")
    private Integer quantityOrder;

    @Column
    private Float discount;

    @Column
    private Float unitPrice;

    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderDetailID implements Serializable {
        private static final long serialVersionUID = 1L;
        @Column
        @Length(max = 15)
        private String orderId;
        @Column
        @Length(max = 15)
        private String productId ;
    }

    // Specify order details relationship
    @ManyToOne
    @JoinColumn(name="orderId", insertable = false, updatable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name="productId", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;
}
