package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.models.User;
import com.example.demo.repository.UserRepository;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

   
    public CustomerUserDetailsService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    // Chargement d'un utilisateur depuis la base de données gràce à l'identifiant
       @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé" + username));

    // puis comparaison grâce à un user vbuilder avec mot de passe et rôle.
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // ex: "USER", "ADMIN"
                .build();
    }

}
