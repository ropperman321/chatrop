package com.chatrop.users.infrastructure.adapter.output.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.chatrop.users.infrastructure.adapter.input.rest.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    // @Bean
    // public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    // http
    // .csrf(csrf -> csrf.disable())
    // .authorizeHttpRequests(auth -> auth
    // .requestMatchers("/api/users/register", "/api/users/login", "/index.html",
    // "/static/**",
    // "/ws/**")
    // .permitAll()
    // .anyRequest().authenticated() // <--- ¡Ahora todo lo demás está bloqueado!
    // )
    // // AÑADIMOS EL FILTRO AQUÍ:
    // .addFilterBefore(jwtAuthenticationFilter,
    // UsernamePasswordAuthenticationFilter.class);

    // return http.build();
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Asegúrate de que esté desactivado
                .cors(Customizer.withDefaults()) // ¡AÑADE ESTO para evitar problemas de origen!
                .authorizeHttpRequests(auth -> auth
                        // 1. Permitimos el login, registro e index
                        .requestMatchers("/api/users/register", "/api/users/login", "/index.html", "/static/**")
                        .permitAll()
                        // 2. PERMITIMOS EL WEBSOCKET (muy importante)
                        .requestMatchers("/ws/**").permitAll()
                        // 3. Todo lo demás requiere estar autenticado
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of(allowedOrigins));
        // ... resto de la configuración

        // @Bean
        // public CorsConfigurationSource corsConfigurationSource() {
        // CorsConfiguration configuration = new CorsConfiguration();
        // configuration.setAllowedOriginPatterns(Arrays.asList("*")); // Permite
        // cualquier origen
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE",
                "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization",
                "Content-Type"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
