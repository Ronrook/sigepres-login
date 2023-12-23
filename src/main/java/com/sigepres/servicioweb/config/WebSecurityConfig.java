package com.sigepres.servicioweb.config;


import com.sigepres.servicioweb.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Clase que configura lo relacionado a las peticiones HTTP
 */
@Configuration // Indica que esta clase es de configuración y va a obtener metodos anotados con @Bean
@EnableWebSecurity // Habilita y configura la seguridad web en la aplicación
@RequiredArgsConstructor // Anotación para crear el contructor con todos los parametros
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;


    /**
     * Configura la seguridad de las peticiones HTTP
     * @param http Peticion a configurar
     * @return SecurityFilterChain
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authRequest ->
                authRequest
                    .requestMatchers("/api/v1/**").permitAll() // permite el acceso a cualquier ruta que comience con este String
                    .anyRequest().authenticated() // Otras rutas se deben autenticar
            )
                .sessionManagement(sessionManager->
                    sessionManager
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
