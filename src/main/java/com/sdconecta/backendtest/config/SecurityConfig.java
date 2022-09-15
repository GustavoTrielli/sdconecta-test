package com.sdconecta.backendtest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/h2/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
        http.csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(HttpMethod.POST, "/users/**")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.DELETE, "/users/**")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.PUT, "/users/**")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.PATCH, "/users/**")
        .hasRole("ADMIN")
        .antMatchers("/login/**")
        .anonymous()
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic();

        return http.build();
    }

}
