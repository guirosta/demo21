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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/products") // Nouvelle url pour le webcontroller
public class ProductWebController {

    @Autowired
    private ProductRepository productRepository;

    // Pahe principale
    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products"; // correspond à templates/products.html
    }

    // Formulaire d'ajout
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form"; // correspond à templates/product-form.html

    }

    // Page de produit
    @GetMapping("/{id}")
    public String showProductDetails(@PathVariable long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produit introuvable"));
        model.addAttribute("product", product);
        return "product-details"; // correspond à templates/product-details.html

    }

    // Création d'un produit
    @PostMapping
    public String createProduct(Product product) {
        productRepository.save(product);
        return "redirect:/products";

    }

}