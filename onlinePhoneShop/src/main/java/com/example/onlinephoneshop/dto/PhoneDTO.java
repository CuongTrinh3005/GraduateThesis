package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO extends ProductDTO{
    private String model;
    @Size(min = 15, max = 15)
    private String imeiNo;
}
