package com.sdconecta.backendtest.dtos;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {
    
    @NotBlank
    @Email
	private String email;
	private String password;
}
