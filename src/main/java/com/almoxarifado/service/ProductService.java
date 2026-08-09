package com.almoxarifado.service;

import com.almoxarifado.database.postgres.ProductDaoPostgres;
import com.almoxarifado.database.sqlite.ProductDaoSQLite;
import com.almoxarifado.model.Product;
import com.almoxarifado.database.ProductRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = (ProductRepository) productRepository;
    }


    public List<Product> getProducts() throws SQLException {
        return productRepository.findAll();
    }

    public boolean verifyExists(String name) throws SQLException {
        return productRepository.existsByName(name);
    }

    public void registerProduct(
            String name,
            String description,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            int quantity,
            int minimumStock) throws SQLException {

        if (name.isBlank()) {
            System.out.println("Por favor, insira um nome válido!");
            return;
        }

        if (productRepository.existsByName(name)) {
            System.out.println("O produto que você está tentando registrar já existe!");
            return;
        }

        Product product = new Product(
                name,
                description,
                purchasePrice,
                sellingPrice,
                quantity,
                minimumStock);

        productRepository.save(product);
    }

    public void increaseAmount(String name, int amount) throws SQLException {
        Product product = findByName(name);
        if (product == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        product.addStock(amount);
        productRepository.updateQuantity(product.getName(), product.getQuantity());
    }

    public void decreaseAmount(String name, int amount) throws SQLException {
        Product product = findByName(name);
        if (product == null) {
            System.out.println("Produto não encontrado.");
            return;
        }
        product.removeStock(amount);
        productRepository.updateQuantity(product.getName(), product.getQuantity());
    }

    private Product findByName(String name) throws SQLException {
        for (Product product : productRepository.findAll()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;
    }

    public void deleteProduct(String name) throws SQLException {
        if (!productRepository.existsByName(name)) {
            System.out.println("Produto não encontrado");
            return;
        }
        productRepository.deleteByName(name);
    }
}
