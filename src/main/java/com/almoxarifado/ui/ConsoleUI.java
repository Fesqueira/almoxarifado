package almoxarifado.ui;

import java.util.Scanner;
import service.ProductService;

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
                    this.executeOption(option);
                    break;
                case 0:
                    running = false;
                    break;
            }

        }

    }

    public void showMenu(){
        System.out.println("############## MENU ##############");
        System.out.println();
        System.out.println("1- Registrar novo produto.");
        System.out.println("0 - Sair");
    }

    public int readOption(){
        String input = scanner.nextLine();
        int option = Integer.parseInt(input);
        return option;
    }

    public void executeOption (int option){

    }
}



