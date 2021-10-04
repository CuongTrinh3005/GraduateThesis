package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private String userId;
    private String productId;
    private String productName;
    private byte[] image;
    private Float score;
    private String comment;
}