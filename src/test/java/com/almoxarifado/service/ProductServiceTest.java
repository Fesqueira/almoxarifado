package com.almoxarifado.service;

import com.almoxarifado.model.Product;
import com.almoxarifado.ui.ConsoleUI;
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
    private ConsoleUI consoleUI;
    private Product product;

    @BeforeEach
    void setup() {
        productService = new ProductService();
        consoleUI = new ConsoleUI(productService);
    }


    @Test
    void canRegisterProduct() {

        productService.registerProduct("Keyboard",
                "Keyboard",
                new BigDecimal(300),
                new BigDecimal(500),
                10,
                2);
        assertEquals("Keyboard", productService.getProducts().getFirst().getName());
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
    @Test
    void canDecreaseAmount(){
        productService.registerProduct("Mouse",
                "Mouse Gamer",
                new BigDecimal("50.00"),
                new BigDecimal("80.00"),
                10,
                2);
        productService.decreaseAmount("Mouse", 3);

        Product product = productService.getProducts().getFirst();

        assertEquals(7, product.getQuantity());
    }

    //TODO add Unit Tests for ProductService class
}
