package com.example.onlinephoneshop.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

import javax.validation.constraints.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 40)
    private String username;
    
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    
    @NotBlank
    @Size(min = 3, max = 50)
    private String fullName;
    
    @Size(min=8, max = 14)
	private String phoneNumber;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> roles;
}