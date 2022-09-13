package com.sdconecta.backendtest.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "TB_CRM")
public class CrmModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "native")
    private int id;
    @Column(name = "crm", nullable = false, length = 45)
	private String crm;
	@Column(name = "uf", nullable = false, length = 2)
	private String uf;
	@Column(name = "specialty", length = 255)
	private String specialty;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

}
