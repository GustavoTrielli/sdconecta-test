package com.sdconecta.backendtest.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sdconecta.backendtest.models.UserModel;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>, JpaSpecificationExecutor<UserModel>{

    boolean existsByEmail(String email);

    Optional<UserModel> findByEmail(String email);

    @Query(value="SELECT * FROM TB_USER u WHERE EXISTS (SELECT c.user_id from TB_CRM c WHERE u.id = c.user_id AND c.specialty ILIKE %?1%)", nativeQuery = true)
    List<UserModel> findBySpecialty(String specialty);

    @Query(value="SELECT * FROM TB_USER u WHERE u.name ILIKE %?1% AND EXISTS (SELECT c.user_id from TB_CRM c WHERE u.id = c.user_id AND c.specialty ILIKE %?2%)", nativeQuery = true)
    List<UserModel> findByNameAndSpecialty(String name, String specialty);


    
}
