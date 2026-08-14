package com.almoxarifado.database.sqlite;

import com.almoxarifado.database.DataClass;
import com.almoxarifado.database.ProductRepository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;


public class ProductDaoSQLite extends DataClass implements ProductRepository {

    @Override
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    public void createSchemaIfNotExists() throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS products (
                id TEXT PRIMARY KEY,
                name TEXT NOT NULL UNIQUE,
                description TEXT,
                purchase_price NUMERIC NOT NULL,
                selling_price NUMERIC NOT NULL,
                quantity INTEGER NOT NULL,
                minimum_stock INTEGER NOT NULL,
                created_at TEXT NOT NULL
            )
            """;

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute(sql);
        }
    }
    @Override
    protected LocalDateTime getCreatedAt(ResultSet resultSet)
            throws SQLException {

        long timestamp = resultSet.getLong("created_at");

        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }
    @Override
    protected UUID getUUID(ResultSet resultSet, String column)
            throws SQLException {

        String value = resultSet.getString(column);

        return UUID.fromString(value);
    }
}
