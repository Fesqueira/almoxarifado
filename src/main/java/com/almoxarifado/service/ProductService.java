
package com.almoxarifado.service;

import com.almoxarifado.model.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.lang.StringBuilder;
import java.lang.Object;

public class ProductService {
    private final List<Product> products;
    StringBuilder stringBuilder = new StringBuilder();
    int arraySize;


    public ProductService (){
        this.products = new ArrayList<>();

    }
    public List<Product> getProducts() {
        return products;
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

        if  (products.stream().map(Product::getName).noneMatch(name::equals) && !name.isBlank()) {
            this.products.add(product);
        }
        else if (name.isBlank()) {
            System.out.println("Por favor, insira um nome válido!");

        } else{ System.out.println("O produto que você está tentando registrar já existe!"); }
    }


    public void increaseAmount(String name, int amount) {
        arraySize = products.size();
        for (int i = 0; i < arraySize; i++){
            if (products.get(i).getName().equalsIgnoreCase(name)){
                products.get(i).addStock(amount);
                break;
            }
        }
    }

    public void decreaseAmount(String name, int amount){
        arraySize = products.size();
        for (int i = 0; i < arraySize; i++){
            if (products.get(i).getName().equalsIgnoreCase(name)){
                products.get(i).removeStock(amount);
                break;
            }
        }
    }
}