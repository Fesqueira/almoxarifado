package com.almoxarifado.database.sqlite;

import com.almoxarifado.model.Product;

import com.almoxarifado.database.ProductRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDaoSQLite implements ProductRepository {

    private static final Logger LOGGER = Logger.getLogger(ProductDaoSQLite.class.getName());


    public void createSchemaIfNotExists() throws SQLException {
        var sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id TEXT PRIMARY KEY, " +
                "name TEXT NOT NULL UNIQUE, " +
                "description TEXT, " +
                "purchase_price NUMERIC NOT NULL, " +
                "selling_price NUMERIC NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "minimum_stock INTEGER NOT NULL, " +
                "created_at TEXT NOT NULL)";

        try (Connection dbconnection = DBConnection.getConnection();
             Statement stmt = dbconnection.createStatement()) {
            stmt.execute(sql);
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        var sql = "INSERT INTO products (id, name, description, purchase_price, selling_price, quantity, minimum_stock, created_at)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection dbconnection = DBConnection.getConnection();
             PreparedStatement statement = dbconnection.prepareStatement(sql)) {

            statement.setString(1, product.getId().toString());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPurchasePrice());
            statement.setBigDecimal(5, product.getSellingPrice());
            statement.setInt(6, product.getQuantity());
            statement.setInt(7, product.getMinimumStock());
            statement.setString(8, product.getCreatedAt().toString());
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o produto id=" + product.getId(), e);
            throw e;
        }
    }

    @Override
    public boolean existsByName(String name) throws SQLException {
        var sql = "SELECT 1 FROM products WHERE name = ? LIMIT 1";
        try (Connection dbconnection = DBConnection.getConnection();
             PreparedStatement stmt = dbconnection.prepareStatement(sql)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public List<Product> findAll() throws SQLException {
        var sql = "SELECT id, name, description, purchase_price, selling_price, quantity, minimum_stock, created_at " +
                "FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection dbconnection = DBConnection.getConnection();
             PreparedStatement stmt = dbconnection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Product product = new Product(
                        UUID.fromString(rs.getString("id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("purchase_price"),
                        rs.getBigDecimal("selling_price"),
                        rs.getInt("quantity"),
                        rs.getInt("minimum_stock"),
                        LocalDateTime.parse(rs.getString("created_at")));
                products.add(product);
            }
        }
        return products;
    }

    @Override
    public void updateQuantity(String name, int newQuantity) throws SQLException {
        var sql = "UPDATE products SET quantity = ? WHERE name = ?";
        try (Connection dbconnection = DBConnection.getConnection();
             PreparedStatement stmt = dbconnection.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteByName(String name) throws SQLException {
        var sql = "DELETE FROM products WHERE name = ?";
        try (Connection dbconnection = DBConnection.getConnection();
             PreparedStatement stmt = dbconnection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }

    }
}
