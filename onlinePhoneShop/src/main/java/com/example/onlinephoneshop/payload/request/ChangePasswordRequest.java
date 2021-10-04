package com.example.onlinephoneshop.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
	@Size(min=3, max = 40)
	private String username;
	@Size(min=4, max = 40)
	private String currentPassword;
	@Size(min=4, max = 40)
	private String newPassword;
}