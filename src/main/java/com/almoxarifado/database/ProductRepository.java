package com.almoxarifado.database;

import com.almoxarifado.model.Product;

import com.almoxarifado.model.Product;

import java.sql.SQLException;
import java.util.List;


public interface ProductRepository {
    void save(Product product) throws SQLException;
    boolean existsByName(String name) throws SQLException;
    List<Product> findAll() throws SQLException;
    void updateQuantity(String name, int newQuantity) throws SQLException;

    void deleteByName(String name) throws SQLException;
}
