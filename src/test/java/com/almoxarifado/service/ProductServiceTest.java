package com.almoxarifado.service;

import com.almoxarifado.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        productService.registerProduct("Mousepad",
                "New",
                new BigDecimal(30),
                new BigDecimal(50),
                10,
                2);
        productService.registerProduct("Keyboard",
                "Keybord",
                new BigDecimal(300),
                new BigDecimal(500),
                10,
                2);
        productService.registerProduct("Mouse",
                "Mouse Gear",
                new BigDecimal(80),
                new BigDecimal(120),
                10,
                2);

        productService.listProducts("normal");

    }

    @Test
    void canIncreaseAmount() {
        productService.registerProduct("Mouse",
                "Mouse Gamer",
                new BigDecimal("50.00"),
                new BigDecimal("80.00"),
                10,
                2);

        productService.increaseAmount("mouse", 5);

        Product product = productService.getProducts().getFirst();

        assertEquals(15, product.getQuantity());



    }

    //TODO add Unit Tests for ProductService class
}
