
package com.almoxarifado.service;

import com.almoxarifado.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private final List<Product> products;

    public ProductService (){
        this.products = new ArrayList<>();

    }

    public boolean verifyExists(Product product, String name){
        return products.stream().map(Product::getName).noneMatch(name::equals);
    }

    public void registerProduct(
            String name,
            String description,
            BigDecimal purchasePrice,
            BigDecimal sellingPrice,
            int quantity,
            int minimumStock) {


        Product product = new Product(
                name,
                description,
                purchasePrice,
                sellingPrice,
                quantity,
                minimumStock);

        if  (products.stream().map(Product::getName).noneMatch(name::equals)) {
            this.products.add(product);
        }
        else{
            System.out.println("O produto que você está tentando registrar já existe!");
        }
    }


}