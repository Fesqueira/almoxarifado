package com.almoxarifado.service;

import com.almoxarifado.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ProductServiceTest {
    private ProductService productService;
    private Product product;

    @BeforeEach
    void setup() {
        productService = new ProductService();
    }

    @Test
    void canRegisterProduct() {

        productService.registerProduct("Keyboard",
                "Keybord",
                new BigDecimal(300),
                new BigDecimal(500),
                10,
                2);

    }

    @Test
    void canCreateList(){

        productService.listProducts();

    }
    //TODO add Unit Tests for ProductService class
}
