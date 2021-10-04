package com.example.onlinephoneshop.entity;

import com.example.onlinephoneshop.enums.RoleName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer roleId;

    @Column(name="role_name", length=60)
    @Enumerated(EnumType.STRING)
    @NaturalId
    private RoleName name;
}
