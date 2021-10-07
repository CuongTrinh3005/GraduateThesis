package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private String userId;
    private String productId;
    private String productName;
    private byte[] image;
    private Date createdDate;
    private Float score;
    private String comment;

//    public RatingDTO(String userId, String productId, String productName, byte[] image, Date createdDate, Float score, String comment) {
//        this.userId = userId;
//        this.productId = productId;
//        this.productName =  productName;
//        this.image = image;
//        this.createdDate = createdDate;
//        this.score = score;
//        this.comment = comment;
//    }
}
