
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

        if  (products.stream().map(Product::getName).noneMatch(name::equals)) {
            this.products.add(product);
        }
        else{
            System.out.println("O produto que você está tentando registrar já existe!");
        }
    }

    public void listProducts(String type){
        if ("add".equals(type)){
            System.out.println("Escreva o nome do produto que deseja incrementar.");
        }
        else{
            System.out.printf(
                 "A lista atualmente possui %d itens.%n",
                    products.size());
        }
        String listOfItems;
        for (Product product : products){

            StringBuilder sb = new StringBuilder();
            sb.append(product.getName())
               .append(" ::::: Quantidade: ")
               .append(product.getQuantity())
               .append(" ::::: Preço: ")
               .append(product.getSellingPrice());

            System.out.println(sb);
        }

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

}