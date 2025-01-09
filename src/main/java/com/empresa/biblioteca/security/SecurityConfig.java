package com.empresa.biblioteca.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.Filter;

@Configuration
public class SecurityConfig<JwtAuthenticationFilter> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private Filter jwtAuthenticationFilter;

    // Configura o AuthenticationManager para autenticar os usuários
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = 
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    // Configuração das permissões de acesso e segurança das rotas
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()  // Desabilitar CSRF para facilitar o uso de APIs
            .authorizeRequests()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Permitir acesso ao Swagger
            .requestMatchers(HttpMethod.POST, "/pessoa/login").permitAll()  // Permitir login sem autenticação
            .requestMatchers(HttpMethod.GET, "/livro/**").permitAll()  // Permitir leitura de livros sem autenticação
            .requestMatchers(HttpMethod.GET, "/pessoa/**").hasRole("USER")  // Requer autenticação para acessar dados de pessoas
            .requestMatchers(HttpMethod.POST, "/livro/**").hasRole("ADMIN")  // Requer admin para criar livros
            .requestMatchers(HttpMethod.PUT, "/livro/**").hasRole("ADMIN")  // Requer admin para editar livros
            .requestMatchers(HttpMethod.DELETE, "/livro/**").hasRole("ADMIN")  // Requer admin para deletar livros
            .anyRequest().authenticated()  // Todas as outras requisições exigem autenticação
            .and()
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Adicionar filtro JWT

        return http.build();  // Retorna a configuração de segurança
    }

    // Definir o PasswordEncoder para codificação de senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usar BCrypt para codificar senhas
    }
}