package com.sdconecta.backendtest.dtos;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserDto {

    private int id;
    @NotBlank
    @Email
	private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
    @NotBlank
	private String name;
	private String surname;
	private String mobilePhone;
	private List<CrmDto> crms;

}
