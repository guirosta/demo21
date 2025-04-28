package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomerUserDetailsService;

@Configuration // Indique que cette classe contient des beans de configuration Spring
@EnableWebSecurity // Active la sécurité Web Spring
public class SecurityConfig {

    private final CustomerUserDetailsService userDetailsService;

    public SecurityConfig(CustomerUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    // Bean pour encoder les mots de passe avec BCrypt
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    // Bean qui relie le service utilisateur à Spring Security et utilise BCrypt
    // pour vérifier les mots de passe
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService); // Utilise le service pour charger les utilisateurs
        provider.setPasswordEncoder(passwordEncoder()); // Création d'une instance de passwordEncoder pou encodre et
                                                        // valider les mdp
        return provider;
    }

    // Bean principal pour configurer la chaîne de filtres de sécurité HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login*").permitAll()
                        // Protège l'accès aux pages /admin/** pour les utilisateurs avec rôle "ADMIN"
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        // Exige une connexion pour accéder à /product-form/**
                        .requestMatchers("/product-form/**").authenticated()
                        // Autorise tout le reste (ex: page d'accueil, liste de produits) sans connexion

                        .anyRequest().permitAll())
                .formLogin(form -> form
                        // Utilise une page de connexion personnalisée à l'URL "/login"

                        .loginPage("/login")
                        .permitAll()) // Permet à tout le monde d'accéder à la page de login
                .logout(logout -> logout
                        .permitAll()) // Permet à tout le monde de se déconnecter
                .build();

    }

}