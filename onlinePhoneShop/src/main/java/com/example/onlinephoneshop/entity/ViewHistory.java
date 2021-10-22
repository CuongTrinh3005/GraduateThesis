package com.example.onlinephoneshop.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.example.onlinephoneshop.entity.Rating.RatingId;
import com.example.onlinephoneshop.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "view_histories")
public class ViewHistory extends AuditModel{
	@EmbeddedId
    private ViewHistoryId viewHistoryId;

    @Column
    @DecimalMin(value="1")
    private Long viewCount=1L;

    @Embeddable
    @Getter @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ViewHistoryId implements Serializable {
        private static final long serialVersionUID = 1L;
        @Column
        @Length(max = 15)
        @NotBlank
        private String userId;
        @Column
        @Length(max = 15)
        @NotBlank
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