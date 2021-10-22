package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDTO extends ProductDTO{
    private String model;
    @Size(min = 15, max = 15)
    @NotBlank
    private String imeiNo;
    @NotNull
    private Integer ram;
    @NotNull
    private Integer batteryPower;
    @NotNull
    private Integer inMemory;
    @NotNull
    private Boolean touchScreen;
    @NotNull
    private Boolean wifi;
    @NotNull
    private Boolean bluetooth;
    private Float clockSpeed;
    private Integer n_cores;
    private Integer n_sim;
    @NotNull
    private Integer pxHeight;
    @NotNull
    private Integer pxWidth;
    @NotNull
    private Float screenHeight;
    @NotNull
    private Float screenWidth;
    private Integer refreshRate;
    private Integer frontCam;
    private String backCam;
    private Boolean support_3G;
    private Boolean support_4G;
    private Boolean support_5G;
    private String otherSpecification;
//    private final Integer type=1;
}