package com.almoxarifado;

import com.almoxarifado.database.sqlite.ProductDaoSQLite;
import com.almoxarifado.service.ProductService;
import com.almoxarifado.ui.ProductGUI;

import javax.swing.SwingUtilities;


public class MainLocal {
    public static void main(String[] args) throws Exception {
        ProductDaoSQLite productDao = new ProductDaoSQLite();
        productDao.createSchemaIfNotExists();

        ProductService productService = new ProductService(productDao);

        SwingUtilities.invokeLater(() -> new ProductGUI(productService).setVisible(true));
    }
}
