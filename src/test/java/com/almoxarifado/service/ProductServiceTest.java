package com.almoxarifado.service;

import com.almoxarifado.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ProductServiceTest {
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
        System.out.println(productService.getProducts().getFirst().getName());
    }

    @Test

    void cantRegisterProduct() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        productService.registerProduct(
                "   ",
                "Descrição qualquer",
                new BigDecimal("100.00"),
                new BigDecimal("150.00"),
                5,
                1
        );

        System.setOut(System.out);

        assertEquals(0, productService.getProducts().size());
        assertTrue(outContent.toString().contains("Por favor, insira um nome válido!"));
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

        productService.listProducts();

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
