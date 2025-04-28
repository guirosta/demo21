package com.example.demo.controllers;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {

    private final UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // correspond à src/main/resources/templates/login.html
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerProcess(@ModelAttribute("user") User user) {
        // sécuriser le mdp
        user.setPassword(passwordEncoder.encode(user.getPassword())); 
        // Définir le rôle du membre inscrit
        user.setRole("USER"); 
        // Sauvegarder dans la db
        userRepository.save(user);
        return "redirect/login";
    }

}
