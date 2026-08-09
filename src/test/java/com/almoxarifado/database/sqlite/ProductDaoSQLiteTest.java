package com.almoxarifado.database.sqlite;

import com.almoxarifado.model.Product;

import com.almoxarifado.database.sqlite.ProductDaoSQLite;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ProductDaoSQLiteTest {

    private File tempDbFile;
    private ProductDaoSQLite dao;

    @BeforeEach
    void setUp() throws IOException, SQLException {
        tempDbFile = File.createTempFile("almoxarifado-test", ".db");
        tempDbFile.delete(); // queremos só o caminho; o SQLite cria o arquivo sozinho
        System.setProperty("almoxarifado.db.path", tempDbFile.getAbsolutePath());

        dao = new ProductDaoSQLite();
        dao.createSchemaIfNotExists();
    }

    @AfterEach
    void tearDown() {
        System.clearProperty("almoxarifado.db.path");
        if (tempDbFile != null) {
            tempDbFile.delete();
        }
    }

    private Product buildProduct(String name) {
        return new Product(
                name,
                "Produto de teste",
                BigDecimal.valueOf(4.5),
                BigDecimal.valueOf(9.9),
                20,
                5);
    }

    @Test
    void saveESeguidoDeExistsByNameEncontraOProduto() throws SQLException {
        dao.save(buildProduct("Chave Allen"));

        assertTrue(dao.existsByName("Chave Allen"));
        assertFalse(dao.existsByName("Não Cadastrado"));
    }

    @Test
    void findAllRetornaTodosOsCamposCorretamente() throws SQLException {
        Product original = buildProduct("Trena");
        dao.save(original);

        List<Product> products = dao.findAll();

        assertEquals(1, products.size());
        Product loaded = products.get(0);
        assertEquals(original.getId(), loaded.getId());
        assertEquals(original.getName(), loaded.getName());
        assertEquals(original.getDescription(), loaded.getDescription());
        assertEquals(0, original.getPurchasePrice().compareTo(loaded.getPurchasePrice()));
        assertEquals(0, original.getSellingPrice().compareTo(loaded.getSellingPrice()));
        assertEquals(original.getQuantity(), loaded.getQuantity());
        assertEquals(original.getMinimumStock(), loaded.getMinimumStock());
        assertEquals(original.getCreatedAt(), loaded.getCreatedAt());
    }

    @Test
    void updateQuantityAlteraApenasOProdutoCorreto() throws SQLException {
        dao.save(buildProduct("Lixa"));
        dao.save(buildProduct("Broca"));

        dao.updateQuantity("Lixa", 99);

        List<Product> products = dao.findAll();
        int lixaQuantity = products.stream()
                .filter(p -> p.getName().equals("Lixa"))
                .findFirst()
                .orElseThrow()
                .getQuantity();
        int brocaQuantity = products.stream()
                .filter(p -> p.getName().equals("Broca"))
                .findFirst()
                .orElseThrow()
                .getQuantity();

        assertEquals(99, lixaQuantity);
        assertEquals(20, brocaQuantity);
    }

    @Test
    void saveComNomeDuplicadoLancaSQLException() throws SQLException {
        dao.save(buildProduct("Serra"));
        Product duplicado = buildProduct("Serra");

        // A constraint UNIQUE em "name" deve rejeitar o segundo insert
        assertThrows(SQLException.class, () -> dao.save(duplicado));
    }

    @Test
    void findAllComBancoVazioRetornaListaVazia() throws SQLException {
        assertEquals(0, dao.findAll().size());
    }
}
