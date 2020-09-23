package com.jibeesh.productinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jibeesh.productinfo.entity.Product;
import com.jibeesh.productinfo.repository.ProductRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllProducts() throws Exception {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        product.setId(5);
        product.setProductName("apple");
        product.setProductDescription("apple");
        product.setPrice(50000D);
        productList.add(product);

        String URL = "/products";
        Mockito.when(productRepository.findAll()).thenReturn(productList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].productName", Matchers.equalTo("apple")))
                .andExpect(jsonPath("$[0].productDescription", Matchers.equalTo("apple")))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(50000D)));
    }

    @Test
    void getProductById() throws Exception {
        Product product = new Product();
        product.setProductName("apple");
        product.setProductDescription("apple");
        product.setPrice(50000D);

        String URL = "/products/101";
        Mockito.when(productRepository.findById(Mockito.anyInt()))
                .thenReturn(java.util.Optional.of(product));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("result.getResponse() = " + result.getResponse().getContentAsString());
        String expected = "{productName:apple,productDescription:apple,price:50000}";
        JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
    }

    @Test
    void saveProduct() throws Exception {
        Product product = new Product();
        product.setId(7);
        product.setProductName("Samsung");
        product.setProductDescription("Samsung");
        product.setPrice(70000D);

        String URL = "/product";
        String json = objectMapper.writeValueAsString(product);
        System.out.println("json = " + json);
        Mockito.when(productRepository.save(product)).thenReturn(product);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(7)))
                .andExpect(jsonPath("$[0].productName", Matchers.equalTo("Samsung")))
                .andExpect(jsonPath("$[0].productDescription", Matchers.equalTo("Samsung")))
                .andExpect(jsonPath("$[0].price", Matchers.equalTo(70000D)));
    }

    @Test
    void deleteProduct() {
    }

    @Test
    void updateProduct() {
    }
}