package com.almoxarifado.model;

import com.almoxarifado.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    private Product newProduct(int quantity) {
        return new Product(
                "Parafuso Sextavado",
                "Parafuso M6 sextavado",
                BigDecimal.valueOf(0.50),
                BigDecimal.valueOf(1.20),
                quantity,
                10);
    }

    @Test
    void mainConstructorGeneratesIdAndDate() {
        Product product = newProduct(50);

        assertNotNull(product.getId());
        assertNotNull(product.getCreatedAt());
    }

    @Test
    void completeConstructorMaintainsTheIdAndDate() {
        UUID id = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.of(2026, 1, 1, 10, 0);

        Product product = new Product(
                id, "Martelo", "Martelo de borracha",
                BigDecimal.valueOf(10), BigDecimal.valueOf(25),
                5, 2, createdAt);

        assertEquals(id, product.getId());
        assertEquals(createdAt, product.getCreatedAt());
    }

    @Test
    void addStockTest() {
        Product product = newProduct(10);

        product.addStock(5);

        assertEquals(15, product.getQuantity());
    }

    @Test
    void addStockZeroExceptionTest() {
        Product product = newProduct(10);

        assertThrows(IllegalArgumentException.class, () -> product.addStock(0));
    }

    @Test
    void addStockNegativeExceptionTest() {
        Product product = newProduct(10);

        assertThrows(IllegalArgumentException.class, () -> product.addStock(-3));
    }

    @Test
    void removeStockTest() {
        Product product = newProduct(10);

        product.removeStock(4);

        assertEquals(6, product.getQuantity());
    }

    @Test
    void removeStockNegativeExceptionTest() {
        Product product = newProduct(10);

        assertThrows(IllegalArgumentException.class, () -> product.removeStock(-1));
    }

    @Test
    void removeStockBiggerThanTest() {
        Product product = newProduct(5);

        assertThrows(IllegalStateException.class, () -> product.removeStock(6));
    }

    @Test
    void removeStockResultZeroTest() {
        Product product = newProduct(5);

        product.removeStock(5);

        assertEquals(0, product.getQuantity());
    }
}
