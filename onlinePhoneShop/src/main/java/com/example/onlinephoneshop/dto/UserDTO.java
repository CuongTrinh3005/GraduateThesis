package com.example.onlinephoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.Date;

@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    @NotNull
    private String username;
    @NotEmpty
    private String fullName;
    @Email
    private String email;
    private String phoneNumber;
    private String address;
    private byte[] image;
    private Boolean gender;
    @Past
    private Date birthday;
    private String roleName="USER";
}