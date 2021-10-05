package com.example.onlinephoneshop.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String username;
    private String userId;
    private String email;
    private List<String> roles;

    public JwtResponse(String token, String username, String userId, String email, List<String> roles) {
        this.token = token;
        this.username = username;
        this.userId = userId;
        this.email = email;
        this.roles = roles;
    }
}