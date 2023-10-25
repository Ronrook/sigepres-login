package com.sigepres.servicioweb.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Clase que configura lo relacionado a las peticiones HTTP
 */
@Configuration // Indica que esta clase es de configuración y va a obtener metodos anotados con @Bean
@EnableWebSecurity // Habilita y configura la seguridad web en la aplicación
@RequiredArgsConstructor // Anotación para crear el contructor con todos los parametros
public class WebSecurityConfig {

    /**
     * Configura la seguridad de las peticiones HTTP
     * @param http Peticion a configurar
     * @return
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authRequest ->
                authRequest
                    .requestMatchers("/auth/**").permitAll() // permite el acceso a cualquier ruta que comience con este String
                    .anyRequest().authenticated() // Otras rutas se deben autenticar
            )
            .formLogin(Customizer.withDefaults())
            .build();
    }
}
