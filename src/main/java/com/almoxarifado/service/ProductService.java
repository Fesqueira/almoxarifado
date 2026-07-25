
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

    public void listProducts(){
        int arraySize = products.size();

        System.out.println(String.format("A lista atualmente possui %d intens:", arraySize));

        for (int i = 0; i < arraySize; i++){
            String listOfItems = String.valueOf(stringBuilder.append(products.get(i).getName())
                                              .append(" ::::: Preço: ")
                                              .append(products.get(i).getSellingPrice()));
            System.out.println(listOfItems);
        }
    }

}