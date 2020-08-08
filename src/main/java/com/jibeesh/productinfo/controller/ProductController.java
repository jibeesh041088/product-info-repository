package com.jibeesh.productinfo.controller;

import com.jibeesh.productinfo.entity.Product;
import com.jibeesh.productinfo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/products/{id}")
    public Optional<Product> getProductById(@PathVariable(name = "id") Integer id) {
        return productRepository.findById(id);
    }

    @PostMapping(value = "/products")
    public List<Product> saveProduct(@RequestBody Product product) {
        productRepository.save(product);
        return productRepository.findAll();
    }

    @DeleteMapping(value = "/product/{id}")
    public List<Product> deleteProduct(@PathVariable(name = "id") Integer id) {
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PutMapping(value = "/products")
    public List<Product> updateProduct(@RequestBody Product product) {
        productRepository.save(product);
        return productRepository.findAll();
    }
}
