package com.example.onlinephoneshop.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "accessories")
@PrimaryKeyJoinColumn(name = "accessoryId")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accessory extends Product{
    @Column(columnDefinition = "ntext")
    private String compatibleDevices;

    @Column(columnDefinition = "ntext")
    private String functions;

    @Column(columnDefinition = "ntext")
    private String otherSpecification;
}