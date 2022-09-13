package com.sdconecta.backendtest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sdconecta.backendtest.models.CrmModel;

public interface CrmRepositoy extends JpaRepository<CrmModel, Integer> {
    
}
