package com.almoxarifado.database.postgres;

import com.almoxarifado.database.ProductRepository;
import com.almoxarifado.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDaoPostgres implements ProductRepository {

    private static final Logger LOGGER = Logger.getLogger(ProductDaoPostgres.class.getName());

    public void createSchemaIfNotExists() throws SQLException {
        var sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id UUID PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL UNIQUE, " +
                "description TEXT, " +
                "purchase_price NUMERIC(12,2) NOT NULL, " +
                "selling_price NUMERIC(12,2) NOT NULL, " +
                "quantity INTEGER NOT NULL, " +
                "minimum_stock INTEGER NOT NULL, " +
                "created_at TIMESTAMP NOT NULL)";

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

            statement.setObject(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setBigDecimal(4, product.getPurchasePrice());
            statement.setBigDecimal(5, product.getSellingPrice());
            statement.setInt(6, product.getQuantity());
            statement.setInt(7, product.getMinimumStock());
            statement.setTimestamp(8, Timestamp.valueOf(product.getCreatedAt()));
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
                        (UUID) rs.getObject("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getBigDecimal("purchase_price"),
                        rs.getBigDecimal("selling_price"),
                        rs.getInt("quantity"),
                        rs.getInt("minimum_stock"),
                        rs.getTimestamp("created_at").toLocalDateTime());
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

    public void deleteByName(String name) throws SQLException {
        var sql = "DELETE FROM products WHERE name = ?";
        try (Connection dbconnection = com.almoxarifado.database.sqlite.DBConnection.getConnection();
             PreparedStatement stmt = dbconnection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }

    }
}
