package com.almoxarifado;


import com.almoxarifado.ui.ConsoleUI;
import com.almoxarifado.service.ProductService;

public class Main {
    static void main(String[] args) {
        ProductService productService = new ProductService();
        ConsoleUI ui = new ConsoleUI(productService);
        ui.start();

    }
}
