package com.decrypto.api.decrypto;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @SuppressWarnings("removal")
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                )
                .headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**"));
        return http.build();
    }
    
    @SuppressWarnings("removal")
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .requestMatchers("/api/comitentes/**").permitAll() // Permite acceso sin autenticación a los endpoints de comitentes
            .anyRequest().authenticated()
            .and()
            .csrf().disable(); // Desactiva CSRF para pruebas, habilitar en producción
    }
    
}