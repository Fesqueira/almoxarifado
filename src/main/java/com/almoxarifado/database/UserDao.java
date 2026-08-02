package com.almoxarifado.database;

import com.almoxarifado.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;


public class UserDao {
    public void save (Product product) throws SQLException {
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

        }catch (SQLException e){
            throw new SQLException();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
