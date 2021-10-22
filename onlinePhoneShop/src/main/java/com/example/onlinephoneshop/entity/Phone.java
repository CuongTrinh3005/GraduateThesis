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
    @NotNull
    private Integer ram;

    @Column
    @NotNull
    private Integer batteryPower;

    @Column
    @NotNull
    private Integer inMemory;

    @Column
    @NotNull
    private Boolean touchScreen;

    @Column
    @NotNull
    private Boolean wifi;

    @Column
    @NotNull
    private Boolean bluetooth;

    @Column
    private Float clockSpeed;

    @Column
    private Integer n_cores;

    @Column
    private Integer n_sim;

    @Column
    @NotNull
    private Integer pxHeight;

    @Column
    @NotNull
    private Integer pxWidth;

    @Column
    @NotNull
    private Float screenHeight;

    @Column
    @NotNull
    private Float screenWidth;

    @Column
    private Integer refreshRate;

    @Column
    private Integer frontCam;

    @Column(columnDefinition = "nvarchar")
    private String backCam;

    @Column
    private Boolean support_3G;

    @Column
    private Boolean support_4G;

    @Column
    private Boolean support_5G;

    @Column(columnDefinition = "ntext")
    private String otherSpecification;

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