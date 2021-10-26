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
    private Integer rom;

    @NotNull
    private Integer batteryPower;

    @NotNull
    private Integer resolution;

    @NotNull
    private Integer maxCore;

    private Float maxSpeed;

    @NotNull
    private Integer refreshRate;

    private Integer simSupport;
    private Integer networks;

    private Integer noFrontCam;

    @NotNull
    private Boolean touchScreen;

    @NotNull
    private Boolean wifi;

    @NotNull
    private Boolean bluetooth;
    private Integer numAccessories;
}