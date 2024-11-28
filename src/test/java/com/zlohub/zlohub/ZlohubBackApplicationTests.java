package com.zlohub.zlohub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
class ZlohubBackApplicationTests {

    @Test
    void contextLoads() {
        // Este método está vazio porque sua única função é verificar se o contexto do Spring Boot carrega corretamente.
    }

    @Test
    void mainMethodRunsSuccessfully() {
        // Assertion adicionada para garantir que nenhuma exceção é lançada
        assertDoesNotThrow(() -> ZlohubBackApplication.main(new String[] {}));
    }
}