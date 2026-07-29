package com.almoxarifado.ui;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.almoxarifado.model.Product;
import com.almoxarifado.service.ProductService;

public class ConsoleUI {
    private final Scanner scanner;
    private final ProductService productService;

    public ConsoleUI(ProductService service) {
        this.scanner = new Scanner(System.in);
        this.productService = service;
    }

    public void start(){
        boolean running = true;

        while (running){
            this.showMenu();
            int option = readOption();
            switch (option){
                case 1:
                    this.registrationScreen();
                    break;
                case 2:
                    this.productListUi();
                    break;
                case 0:
                    System.out.println("Saindo do programa...");
                    running = false;
                    break;
                default:
                    System.out.println("Insira um número correspondente a uma opção válida!");
                    break;
            }

        }

    }

    public void showMenu(){
        System.out.println("############## MENU ##############");
        System.out.println();
        System.out.println("1 - Registrar novo produto.");
        System.out.println("2 - Exibir lista de produtos.");
        System.out.println("0 - Sair");

    }

    public int readOption(){
        String input = scanner.nextLine();
        return Integer.parseInt(input);
    }

    public void registrationScreen(){
        String name;
        String description;
        BigDecimal purchasePrice = BigDecimal.valueOf(0);
        BigDecimal sellingPrice = BigDecimal.valueOf(0);
        int quantity = 0;
        int minimumSet = 0;
        boolean i = true;


        System.out.println("Nome do Produto: ");
        name = scanner.nextLine();
        System.out.println("Descrição do Produto: ");
        description = scanner.nextLine();
        System.out.println("Preço pago pelo produto (Apenas números): ");
        try {
            purchasePrice = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira números válidos.");
        }
        System.out.println("Preço cobrado pelo produto (Apenas números): ");
        try {
            sellingPrice = new BigDecimal(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira números válidos.");
        }
        System.out.println("Quantas unidades: ");
        try {
            quantity = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira números inteiros válidos.");
        }
        System.out.println("Qual a quantidade mínima de estoque deste produto: : ");
        try {
            minimumSet = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira números inteiros válidos.");
        }

        productService.registerProduct(name, description, purchasePrice, sellingPrice, quantity, minimumSet);



    }

    public void listProducts(){
        for (Product product : productService.getProducts()){

            StringBuilder sb = new StringBuilder();
            sb.append(product.getName())
                    .append(" ::::: Quantidade: ")
                    .append(product.getQuantity())
                    .append(" ::::: Preço: ")
                    .append(product.getSellingPrice());

            System.out.println(sb);
        }

    }

    public void productListUi(){
        System.out.printf("Você atualmente possui %d itens em estoque:%n", productService.getProducts().size());
        listProducts();
    }

    public void increaseQuantityUi() {
        System.out.println("Escreva o nome do item que você deseja incrementar no estoque:");
        listProducts();
        String toAdd = scanner.nextLine();
        System.out.println("Quantas unidades você quer aumentar no estoque? ");
        int quantity = Integer.parseInt(scanner.nextLine());
        productService.increaseAmount(toAdd,quantity);
        System.out.println("Estoque incrementado com sucesso!");
    }
}



