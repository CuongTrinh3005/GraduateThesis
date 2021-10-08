package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessoryDTO extends ProductDTO{
    private String compatibleDevices;
    private String functions;
    private final Integer type=2;
}