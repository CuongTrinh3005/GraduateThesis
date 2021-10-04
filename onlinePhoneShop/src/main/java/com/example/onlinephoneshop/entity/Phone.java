package com.example.onlinephoneshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    @Size(min = 15, max = 15)
    private String imeiNo;

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