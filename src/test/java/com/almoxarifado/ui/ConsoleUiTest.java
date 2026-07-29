package com.almoxarifado.ui;

import com.almoxarifado.model.Product;
import com.almoxarifado.service.ProductService;
import com.almoxarifado.ui.ConsoleUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConsoleUiTest {
    private ConsoleUI consoleUI;
    private Product product;
    private ProductService productService;

    @BeforeEach

    void setup(){
        product = new Product();
        productService = new ProductService();
        consoleUI = new ConsoleUI(productService);
    }

    @Test

    public void canListProducts(){
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

            consoleUI.listProducts();

        }
    }


