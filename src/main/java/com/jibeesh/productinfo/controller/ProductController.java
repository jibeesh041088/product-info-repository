package com.jibeesh.productinfo.controller;

import com.jibeesh.productinfo.entity.Product;
import com.jibeesh.productinfo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        System.out.println("Get All Data.......");
        return productRepository.findAll();
    }

    @GetMapping(value = "/products/{id}")
    @Cacheable(value = "products", key = "#id")
    public Optional<Product> getProductById(@PathVariable(name = "id") Integer id) {
        System.out.println("Data from database" + id);
        return productRepository.findById(id);
    }

    @PostMapping(value = "/products")
    @CachePut(value = "products", key = "#id")
    public List<Product> saveProduct(@RequestBody Product product) {
        System.out.println("Save data........");
        productRepository.save(product);
        return productRepository.findAll();
    }

    @DeleteMapping(value = "/product/{id}")
    @CacheEvict(value = "products", key = "#id")
    public List<Product> deleteProduct(@PathVariable(name = "id") Integer id) {
        System.out.println("Delete data...........");
        productRepository.deleteById(id);
        return productRepository.findAll();
    }

    @PutMapping(value = "/products")
    @CachePut(value = "products", key = "#id")
    public List<Product> updateProduct(@RequestBody Product product) {
        System.out.println("Update data........");
        productRepository.save(product);
        return productRepository.findAll();
    }
}
