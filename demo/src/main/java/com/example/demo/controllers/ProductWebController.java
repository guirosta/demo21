package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/products") // Nouvelle url pour le webcontroller
public class ProductWebController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products"; // correspond à templates/products.html
    }

    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable long id, Model model) {
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Produit introuvable")) ;
        model.addAttribute("product", product);
        return "product-details"; // correspond à templates/product-details.html

    }
}