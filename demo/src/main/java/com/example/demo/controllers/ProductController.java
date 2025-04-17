package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // GET all products
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();

    }

    // GET products by Id
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable long id) {
        return productRepository.findById(id).orElse(null);

    }

    // POST Create product
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);

    }

    // PUT Update product
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable long id, @RequestBody Product product) {
        // Recherche du produit désiré
        Product produitExistant = productRepository
                .findById(id).orElseThrow(() -> new RuntimeException("Ce produit n'existe pas"));
        // Mise à des jours des attributs du produit
        produitExistant.setName(product.getName());
        produitExistant.setDescription((product.getDescription()));
        produitExistant.setPrice(product.getPrice());
        produitExistant.setImage(product.getImage());

        return productRepository.save(produitExistant);

    }

    // DELETE product
    @DeleteMapping("/{id}")
    public void deleteProduct (@PathVariable long id) {
        productRepository.deleteById(id);


    }
}
