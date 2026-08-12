package com.almoxarifado.service;

import com.almoxarifado.database.ProductRepository;
import com.almoxarifado.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(productRepository);
    }

    private Product buildProduct(String name, int quantity) {
        return new Product(
                UUID.randomUUID(), name, "Descrição",
                BigDecimal.valueOf(5), BigDecimal.valueOf(10),
                quantity, 2, LocalDateTime.now());
    }

    // ---------- registerProduct ----------

    @Test
    void registerProductComNomeValidoESemDuplicataSalvaProduto() throws SQLException {
        when(productRepository.existsByName("Chave de Fenda")).thenReturn(false);

        productService.registerProduct(
                "Chave de Fenda", "Chave Phillips", BigDecimal.valueOf(3), BigDecimal.valueOf(8), 20, 5);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(productRepository).save(captor.capture());
        assertEquals("Chave de Fenda", captor.getValue().getName());
    }

    @Test
    void registerProductComNomeEmBrancoNaoSalva() throws SQLException {
        productService.registerProduct("   ", "Descrição", BigDecimal.ONE, BigDecimal.TEN, 1, 1);

        verify(productRepository, never()).save(any());
    }

    @Test
    void registerProductComNomeJaExistenteNaoSalva() throws SQLException {
        when(productRepository.existsByName("Martelo")).thenReturn(true);

        productService.registerProduct("Martelo", "Descrição", BigDecimal.ONE, BigDecimal.TEN, 1, 1);

        verify(productRepository, never()).save(any());
    }

    // ---------- verifyExists ----------

    @Test
    void verifyExistsDelegaParaORepositorio() throws SQLException {
        when(productRepository.existsByName("Serrote")).thenReturn(true);

        assertTrue(productService.verifyExists("Serrote"));
        verify(productRepository).existsByName("Serrote");
    }

    // ---------- increaseAmount ----------

    @Test
    void increaseAmountAtualizaQuantidadeQuandoProdutoExiste() throws SQLException {
        Product product = buildProduct("Furadeira", 10);
        when(productRepository.findAll()).thenReturn(List.of(product));

        productService.increaseAmount("Furadeira", 5);

        verify(productRepository).updateQuantity("Furadeira", 15);
    }

    @Test
    void increaseAmountEhCaseInsensitive() throws SQLException {
        Product product = buildProduct("Furadeira", 10);
        when(productRepository.findAll()).thenReturn(List.of(product));

        productService.increaseAmount("furadeira", 5);

        verify(productRepository).updateQuantity("Furadeira", 15);
    }

    @Test
    void increaseAmountComProdutoInexistenteNaoChamaUpdate() throws SQLException {
        when(productRepository.findAll()).thenReturn(List.of());

        productService.increaseAmount("Não Existe", 5);

        verify(productRepository, never()).updateQuantity(anyString(), org.mockito.ArgumentMatchers.anyInt());
    }

    // ---------- decreaseAmount ----------

    @Test
    void decreaseAmountAtualizaQuantidadeQuandoProdutoExiste() throws SQLException {
        Product product = buildProduct("Alicate", 10);
        when(productRepository.findAll()).thenReturn(List.of(product));

        productService.decreaseAmount("Alicate", 4);

        verify(productRepository).updateQuantity("Alicate", 6);
    }

    @Test
    void decreaseAmountMaiorQueEstoqueLancaExcecaoENaoAtualiza() throws SQLException {
        Product product = buildProduct("Alicate", 3);
        when(productRepository.findAll()).thenReturn(List.of(product));

        // Product.removeStock recusa remover mais do que há em estoque
        org.junit.jupiter.api.Assertions.assertThrows(
                IllegalStateException.class,
                () -> productService.decreaseAmount("Alicate", 10));

        verify(productRepository, never()).updateQuantity(anyString(), org.mockito.ArgumentMatchers.anyInt());
    }

    @Test
    void decreaseAmountComProdutoInexistenteNaoChamaUpdate() throws SQLException {
        when(productRepository.findAll()).thenReturn(List.of());

        productService.decreaseAmount("Não Existe", 1);

        verify(productRepository, never()).updateQuantity(anyString(), org.mockito.ArgumentMatchers.anyInt());
    }
}
