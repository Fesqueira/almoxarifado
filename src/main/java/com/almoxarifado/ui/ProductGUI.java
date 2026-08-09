package com.almoxarifado.ui;

import com.almoxarifado.model.Product;
import com.almoxarifado.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;


public class ProductGUI extends JFrame {
    private final ProductService productService;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ProductGUI(ProductService service) {
        super("Almoxarifado - Controle de Estoque");
        this.productService = service;

        this.tableModel = new DefaultTableModel(
                new Object[]{"Nome", "Descrição", "Preço de Compra", "Preço de Venda", "Quantidade", "Estoque Mínimo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.table = new JTable(tableModel);
        this.table.setFont(new Font("SansSerif", Font.PLAIN, 20));
        this.table.setRowHeight(35);

        this.table.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 20)
        );

        this.table.getTableHeader().setPreferredSize(
                new Dimension(0, 45)
        );

        buildLayout();
        refreshTable();


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setMinimumSize(new Dimension(1024, 720));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void buildLayout() {
        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

        Font buttonFont = new Font("SansSerif", Font.PLAIN, 30);

        JPanel buttonPanel = new JPanel();
        JButton registerButton = new JButton("Registrar Produto");
        JButton increaseButton = new JButton("Aumentar Estoque");
        JButton decreaseButton = new JButton("Diminuir Estoque");
        JButton deleteButton = new JButton("Remover Produto");
        JButton refreshButton = new JButton("Atualizar Lista");

        registerButton.addActionListener(e -> registerProductDialog());
        increaseButton.addActionListener(e -> changeStockDialog(true));
        decreaseButton.addActionListener(e -> changeStockDialog(false));
        deleteButton.addActionListener(e -> deleteProductDialog());
        refreshButton.addActionListener(e -> refreshTable());

        registerButton.setPreferredSize(new Dimension(500, 100));
        increaseButton.setPreferredSize(new Dimension(500, 100));
        decreaseButton.setPreferredSize(new Dimension(500, 100));
        refreshButton.setPreferredSize(new Dimension(500, 100));
        deleteButton.setPreferredSize(new Dimension(500, 100));


        deleteButton.setFont(buttonFont);
        registerButton.setFont(buttonFont);
        increaseButton.setFont(buttonFont);
        decreaseButton.setFont(buttonFont);
        refreshButton.setFont(buttonFont);

        buttonPanel.add(registerButton);
        buttonPanel.add(increaseButton);
        buttonPanel.add(decreaseButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);


        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        try {
            tableModel.setRowCount(0);
            List<Product> products = productService.getProducts();
            for (Product product : products) {
                tableModel.addRow(new Object[]{
                        product.getName(),
                        product.getDescription(),
                        product.getPurchasePrice(),
                        product.getSellingPrice(),
                        product.getQuantity(),
                        product.getMinimumStock()
                });
            }
        } catch (SQLException e) {
            showError("Erro ao carregar produtos: " + e.getMessage());
        }
    }

    private void registerProductDialog() {

        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField purchasePriceField = new JTextField();
        JTextField sellingPriceField = new JTextField();
        JTextField quantityField = new JTextField();
        JTextField minimumStockField = new JTextField();

        // Fonte
        Font popUpFont = new Font("SansSerif", Font.PLAIN, 25);

        // Tamanho dos campos
        Dimension fieldSize = new Dimension(400, 45);

        nameField.setPreferredSize(fieldSize);
        descriptionField.setPreferredSize(fieldSize);
        purchasePriceField.setPreferredSize(fieldSize);
        sellingPriceField.setPreferredSize(fieldSize);
        quantityField.setPreferredSize(fieldSize);
        minimumStockField.setPreferredSize(fieldSize);

        // Fonte dos campos
        nameField.setFont(popUpFont);
        descriptionField.setFont(popUpFont);
        purchasePriceField.setFont(popUpFont);
        sellingPriceField.setFont(popUpFont);
        quantityField.setFont(popUpFont);
        minimumStockField.setFont(popUpFont);

        // Labels
        JLabel nameLabel = new JLabel("Nome:");
        JLabel descriptionLabel = new JLabel("Descrição:");
        JLabel purchaseLabel = new JLabel("Preço de Compra:");
        JLabel sellingLabel = new JLabel("Preço de Venda:");
        JLabel quantityLabel = new JLabel("Quantidade:");
        JLabel minimumLabel = new JLabel("Estoque Mínimo:");

        // Fonte dos labels
        nameLabel.setFont(popUpFont);
        descriptionLabel.setFont(popUpFont);
        purchaseLabel.setFont(popUpFont);
        sellingLabel.setFont(popUpFont);
        quantityLabel.setFont(popUpFont);
        minimumLabel.setFont(popUpFont);



        // Painel
        JPanel panel = new JPanel(new GridLayout(0, 1, 8, 8));

        panel.add(nameLabel);
        panel.add(nameField);

        panel.add(descriptionLabel);
        panel.add(descriptionField);

        panel.add(purchaseLabel);
        panel.add(purchasePriceField);

        panel.add(sellingLabel);
        panel.add(sellingPriceField);

        panel.add(quantityLabel);
        panel.add(quantityField);

        panel.add(minimumLabel);
        panel.add(minimumStockField);


        JOptionPane optionPane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
        );


        JDialog dialog = optionPane.createDialog(
                this,
                "Registrar Produto"
        );


        dialog.setSize(800, 700);


        dialog.setResizable(false);


        dialog.setLocationRelativeTo(this);

        for (Component component : optionPane.getComponents()) {

            if (component instanceof JPanel panelButtons) {

                for (Component buttonComponent : panelButtons.getComponents()) {

                    if (buttonComponent instanceof JButton button) {

                        button.setFont(new Font("SansSerif", Font.PLAIN, 18));
                        button.setPreferredSize(new Dimension(120, 50));
                    }
                }
            }
        }


        dialog.setVisible(true);


        Object selectedValue = optionPane.getValue();

        if (selectedValue == null ||
                !selectedValue.equals(JOptionPane.OK_OPTION)) {
            return;
        }


        try {

            productService.registerProduct(
                    nameField.getText(),
                    descriptionField.getText(),
                    new BigDecimal(purchasePriceField.getText()),
                    new BigDecimal(sellingPriceField.getText()),
                    Integer.parseInt(quantityField.getText()),
                    Integer.parseInt(minimumStockField.getText())
            );

            refreshTable();

        } catch (NumberFormatException e) {

            showError("Por favor, insira valores numéricos válidos.");

        } catch (SQLException e) {

            showError("Erro ao salvar produto: " + e.getMessage());
        }
    }

    private void deleteProductDialog(){
        String title = "Remover Produto";

        String name = showInputDialog(
                title,
                "Nome do Produto:"
        );

        if (name == null || name.isBlank()){
            return;
        }
        try {
            productService.deleteProduct(name);
            refreshTable();

        } catch (SQLException e) {
            showError("Erro ao incluir produto: " + e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e){
            showError(e.getMessage());
        }



    }


    private void changeStockDialog(boolean increase) {

        String title = increase
                ? "Aumentar Estoque"
                : "Diminuir Estoque";

        String name = showInputDialog(
                title,
                "Nome do produto:"
        );

        if (name == null || name.isBlank()) {
            return;
        }

        String amountText = showInputDialog(
                title,
                "Quantidade:"
        );

        if (amountText == null || amountText.isBlank()) {
            return;
        }

        try {

            int amount = Integer.parseInt(amountText);

            if (increase) {
                productService.increaseAmount(name, amount);
            } else {
                productService.decreaseAmount(name, amount);
            }

            refreshTable();

        } catch (NumberFormatException e) {

            showError("Por favor, insira um número inteiro válido.");

        } catch (IllegalStateException | IllegalArgumentException e) {

            showError(e.getMessage());

        } catch (SQLException e) {

            showError("Erro ao atualizar estoque: " + e.getMessage());
        }
    }

    private String showInputDialog(String title, String message) {

        Font popUpFont = new Font("SansSerif", Font.PLAIN, 30);

        JLabel label = new JLabel(message);
        label.setFont(popUpFont);

        JTextField textField = new JTextField();
        textField.setFont(popUpFont);
        textField.setPreferredSize(new Dimension(400, 45));

        JPanel panel = new JPanel(new GridLayout(2, 1, 8, 8));

        panel.add(label);
        panel.add(textField);

        JOptionPane optionPane = new JOptionPane(
                panel,
                JOptionPane.PLAIN_MESSAGE,
                JOptionPane.OK_CANCEL_OPTION
        );

        JDialog dialog = optionPane.createDialog(
                this,
                title
        );

        dialog.setSize(500, 300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(this);

        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();

        if (selectedValue == null ||
                !selectedValue.equals(JOptionPane.OK_OPTION)) {
            return null;
        }

        return textField.getText();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}
