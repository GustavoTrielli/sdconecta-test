package com.sdconecta.backendtest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sdconecta.backendtest.dtos.LoginDto;
import com.sdconecta.backendtest.services.AuthService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {
    
    @Autowired
    AuthService authService;

    @PostMapping("/login")
    ResponseEntity<Object> login(@RequestBody @Valid LoginDto loginDto) {

        return authService.authenticate(loginDto);
    }
}
