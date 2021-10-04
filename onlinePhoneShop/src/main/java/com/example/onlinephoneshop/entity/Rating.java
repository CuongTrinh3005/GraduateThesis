package com.example.onlinephoneshop.entity;

import com.example.onlinephoneshop.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ratings")
public class Rating extends AuditModel {
    @EmbeddedId
    private RatingId ratingId;

    @Column
    @DecimalMin(value="0")
    @DecimalMax(value="5")
    private Float score;

    @Column(name="comment")
    private String comment;

    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RatingId implements Serializable {
        private static final long serialVersionUID = 1L;
        @Column
        @NotBlank
        private String userId;
        @Column
        @NotNull
        private String productId ;
    }

    @ManyToOne
    @JoinColumn(name="productId", insertable = false, updatable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne
    @JoinColumn(name="userId", insertable = false, updatable = false)
    @JsonIgnore
    private User user;
}
