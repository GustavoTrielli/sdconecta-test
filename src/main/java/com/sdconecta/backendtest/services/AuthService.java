package com.sdconecta.backendtest.services;

import java.time.LocalDateTime;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;

import com.sdconecta.backendtest.dtos.LoginDto;
import com.sdconecta.backendtest.dtos.PartnerCredentialsDto;
import com.sdconecta.backendtest.dtos.UserDto;
import com.sdconecta.backendtest.dtos.UserTokenDto;
import com.sdconecta.backendtest.models.UserModel;
import com.sdconecta.backendtest.repositories.UserRepository;

import reactor.core.publisher.Mono;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private WebClient webClient;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Value("${sdconecta.client_id}")
    private String clientId;

    @Value("${sdconecta.client_secret}")
    private String clientSecret;

    private PartnerCredentialsDto authPartnerToken = new PartnerCredentialsDto();

    public ResponseEntity<Object> authenticate(@Valid LoginDto loginDto) {

        // Authenticate Application
        Authentication authObject;
        try {
            authObject = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObject);

        } catch (BadCredentialsException e) {
            throw e;
        }

        UserModel userModel = userRepository.findByEmail(loginDto.getEmail()).get();
        UserDto userDto = modelMapper.map(userModel, UserDto.class);

        // Authenticate SD Conecta
        try {
            if(this.authPartnerToken.getAccessToken() == null || (this.authPartnerToken.getExpiresAt().compareTo(LocalDateTime.now()) == -1)) {
                authPartnerToken = this.authPartner();
            }
            var response = this.authUser(userDto, authPartnerToken.getAccessToken());
            userModel.setAuthenticationStatus(response.getAuthorizationStatus());
            userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        }

    }

    private PartnerCredentialsDto authPartner() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "client_credentials");
        PartnerCredentialsDto response = webClient.post()
                .uri("oauth2/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.error(new ResponseStatusException(res.statusCode())))
                .bodyToMono(PartnerCredentialsDto.class)
                .block();

        response.setExpiresAt(LocalDateTime.now().plusSeconds(response.getExpiresIn()));
        return response;

    }

    private UserTokenDto authUser(UserDto userDto, String token) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "client_credentials");
        UserTokenDto response = webClient.post()
                .uri("api/v2/partners/generate-user-token")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(BodyInserters.fromValue(userDto))
                .retrieve()
                .onStatus(HttpStatus::isError, res -> Mono.error(new ResponseStatusException(res.statusCode())))
                .bodyToMono(UserTokenDto.class)
                .block();
        return response;

    }

}
