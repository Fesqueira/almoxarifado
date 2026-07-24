package com.almoxarifado.model;
import java.time.LocalDateTime;
import java.util.UUID;
import java.math.BigDecimal;

public class Product {
    private final UUID id;
    private String name;
    private String description;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;
    private int quantity;
    private int minimumStock;
    private final LocalDateTime createdAt;

    public Product()
    {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();
    }
    public Product(
            String name,
            String description,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            int quantity,
            int minimumStock
    ) {
        this.id = UUID.randomUUID();
        this.createdAt = LocalDateTime.now();

        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.minimumStock = minimumStock;
    }


    public void addStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Você não pode adicionar números negativos."
            );
        }
        this.quantity += amount;
    }

    public void removeStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Você não pode subtrair números negativos!"
            );
        }
        if (amount > this.quantity) {
            throw new IllegalStateException(
                    "Não é possível remover " + amount + " unidades. Apenas " + this.quantity + " disponiveis."
            );
        }
        this.quantity -= amount;
    }

    public String getName() {
        return this.name;
    }

    public int getQuantity() {
        return this.quantity;
    }
}
