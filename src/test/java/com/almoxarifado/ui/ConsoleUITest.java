package com.almoxarifado.ui;

import com.almoxarifado.database.ProductRepository;
import com.almoxarifado.service.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConsoleUITest {

    private final InputStream originalIn = System.in;

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
    }

    private ConsoleUI buildConsoleUI(String simulatedInput) {
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8)));
        ProductRepository repository = Mockito.mock(ProductRepository.class);
        return new ConsoleUI(new ProductService(repository));
    }

    @Test
    void readOptionComNumeroValidoRetornaOMesmoNumero() {
        ConsoleUI ui = buildConsoleUI("2\n");

        assertEquals(2, ui.readOption());
    }

    @Test
    void readOptionComTextoInvalidoRetornaMenosUmEmVezDeQuebrar() {
        ConsoleUI ui = buildConsoleUI("abc\n");

        assertEquals(-1, ui.readOption());
    }

    @Test
    void readOptionComLinhaEmBrancoRetornaMenosUm() {
        ConsoleUI ui = buildConsoleUI("\n");

        assertEquals(-1, ui.readOption());
    }
}
