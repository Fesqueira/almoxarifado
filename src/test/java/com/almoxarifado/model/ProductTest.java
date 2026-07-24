package com.almoxarifado.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {
    private Product product;
    @BeforeEach
    void setup() {
        product = new Product();
    }

    @Test
    void canAddStock() {
        product.addStock(1);
        assertEquals(1, product.getQuantity());
    }
    //TODO add Unit Tests for Product class
}
