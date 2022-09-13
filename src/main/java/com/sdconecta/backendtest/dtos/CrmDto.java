package com.sdconecta.backendtest.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CrmDto {

    @NotBlank
    @Size(max = 45)
	private String crm;
    @NotBlank
    @Size(max = 2)
	private String uf;
    @Size(max = 255)
    private String specialty;
}
