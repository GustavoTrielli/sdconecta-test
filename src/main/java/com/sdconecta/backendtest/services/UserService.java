package com.sdconecta.backendtest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdconecta.backendtest.models.UserModel;
import com.sdconecta.backendtest.models.UserSpecifications;
import com.sdconecta.backendtest.repositories.CrmRepositoy;
import com.sdconecta.backendtest.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    CrmRepositoy crmRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserModel save(UserModel userModel) {
        if (userModel.getCrms() != null) {
            crmRepository.saveAll(userModel.getCrms().stream().map(crm -> {
                crm.setUser(userModel);
                return crm;
            }).collect(Collectors.toList()));
        }
        userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));

        return userRepository.save(userModel);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<UserModel> findAll(Optional<String> name, Optional<String> specialty) {
        if (name.isPresent() && specialty.isPresent()) {

            return userRepository.findByNameAndSpecialty(name.get(), specialty.get());

        } else if (name.isPresent()) {
            Specification<UserModel> specification = UserSpecifications.hasNameILike(name.get()).or(UserSpecifications.hasSurnameILike(name.get()));
            return userRepository.findAll(specification);

        } else if (specialty.isPresent()) {
            // Specification<UserModel> specification = UserSpecifications.hasSpecialty(specialty.get());
            return userRepository.findBySpecialty(specialty.get());

        } else {

            return userRepository.findAll();
        }
        
    }

    public Optional<UserModel> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }
}
