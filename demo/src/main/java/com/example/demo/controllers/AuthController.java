package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // correspond à src/main/resources/templates/login.html
    }
}
