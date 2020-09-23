package com.jibeesh.productinfo.controller;

import com.jibeesh.productinfo.entity.Product;
import com.jibeesh.productinfo.exception.ProductNotFoundException;
import com.jibeesh.productinfo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/api")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/products")
    @Cacheable(value = "products")
    public List<Product> getAllProducts() {
        System.out.println("Get All Data.......");
        System.out.println("Test Example2334");
        return productRepository.findAll();
    }

    @GetMapping(value = "/products/{id}")
    @Cacheable(value = "products", key = "#id")
    public Product getProductById(@PathVariable(name = "id") Integer id) throws ProductNotFoundException {
        System.out.println("Data from database" + id);
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product is not found with id : "+ id));
    }

    @PostMapping(value = "/product")
    @CachePut(value = "products", key = "#product.id")
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

    @PutMapping(value = "/product")
    @CachePut(value = "products", key = "#product.id")
    public List<Product> updateProduct(@RequestBody Product product) {
        System.out.println("Update data........");
        productRepository.save(product);
        return productRepository.findAll();
    }
}
