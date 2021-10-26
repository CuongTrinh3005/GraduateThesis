package com.example.onlinephoneshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "phoneId")
@Table(name = "phones", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "imeiNo"
        })
})
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Phone extends Product{
    @Column
    private String model;

    @Column
    @NotBlank
    @Size(min = 15, max = 15)
    private String imeiNo;

    @Column
    private Integer ram;

    @Column
    private Integer rom;

    @Column
    private Integer batteryPower;

    @Column
    private Integer resolution;

    @Column
    private Integer maxCore;

    @Column
    private Float maxSpeed;

    @Column
    private Integer refreshRate;

    @Column
    private Integer simSupport;

    @Column
    private Integer networks;

    @Column
    private Integer noFrontCam;

    @Column
    private Boolean touchScreen;

    @Column
    private Boolean wifi;

    @Column
    private Boolean bluetooth;

    @ManyToMany
    @JoinTable(
            name = "phone_accessories",
            joinColumns =
                    { @JoinColumn(
                            name = "productId") },
            inverseJoinColumns =
                    { @JoinColumn(
                            name = "accessoryId") })
    private Set<Accessory> accessories;
}