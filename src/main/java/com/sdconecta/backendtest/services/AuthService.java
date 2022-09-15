package com.sdconecta.backendtest.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.sdconecta.backendtest.dtos.LoginDto;


@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    
 public Object authenticate(@Valid LoginDto loginDto) {
        Authentication authObject;
        try {
            authObject = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);
        } catch (BadCredentialsException e){
            throw e;
        }
        return "LOGADO";
    } 

}
