package com.sdconecta.backendtest.dtos;

import java.util.List;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserUpdateDto {

    @Email
	private String email;
	private String password;
	private String name;
	private String surname;
	private String mobilePhone;
	private List<CrmDto> crms;
    
}
