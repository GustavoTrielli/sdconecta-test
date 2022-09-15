package com.sdconecta.backendtest.models;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_USER")
public class UserModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
	private int id;
	@Column(name = "email", nullable = false, unique = true, length = 255)
	private String email;
	@Column(name = "password", nullable = false, length = 255)
	private String password;
	@Column(name = "name", nullable = false, length = 255)
	private String name;
	@Column(name = "surname", length = 255)
	private String surname;
	@Column(name = "mobile_phone", length = 255)
	private String mobilePhone;
	@Column(name = "admin", columnDefinition = "boolean default false")
	private boolean admin;
	@Column(name = "authentication_status", length = 255)
	private String authenticationStatus;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CrmModel> crms;
}
