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
    private Float ramScore;

    @NotNull
    private Float romScore;

    @NotNull
    private Float batteryPowerScore;

    @NotNull
    private Integer resolutionScore;

    @NotNull
    private Integer maxCore;

    private Float maxSpeed;

    @NotNull
    private Integer refreshRateScore;

    private Integer simSupportScore;
    private Integer networksScore;

    private Integer noFrontCam;

    @NotNull
    private Boolean touchScreen;

    @NotNull
    private Boolean wifi;

    @NotNull
    private Boolean bluetooth;
    private Integer numAccessories;
}