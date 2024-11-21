package com.zlohub.zlohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlohub.zlohub.model.Cuidador;
import com.zlohub.zlohub.repository.CuidadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CuidadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CuidadorRepository cuidadorRepository;

    private Cuidador cuidador;

    @BeforeEach
    void setUp() {
        cuidadorRepository.deleteAll();
        cuidador = new Cuidador();
        cuidador.setNome("João");
        cuidador.setSobrenome("Silva");
        cuidador.setCpf("123.456.789-00");
        cuidador.setPoliticaAceita(true);
        cuidador.setEmailContato("joao.silva@example.com"); // Campo obrigatório preenchido
        cuidador.setTelefoneContato1("(11) 99999-9999");    // Campo obrigatório preenchido
        cuidadorRepository.save(cuidador);
    }

    @Test
    void testCriarCuidador() throws Exception {
        Cuidador novoCuidador = new Cuidador();
        novoCuidador.setNome("Carlos");
        novoCuidador.setSobrenome("Souza");
        novoCuidador.setCpf("987.654.321-00");
        novoCuidador.setPoliticaAceita(true);
        novoCuidador.setEmailContato("carlos.souza@example.com");
        novoCuidador.setTelefoneContato1("(11) 91234-5678");
        novoCuidador.setLogradouro("Rua das Flores");
        novoCuidador.setNumero("123");
        novoCuidador.setBairro("Centro");
        novoCuidador.setCidade("São Paulo");
        novoCuidador.setEstado("SP");
        novoCuidador.setCep("01000-000");
        novoCuidador.setTempoExperiencia(3);
        novoCuidador.setFormacao("Enfermagem");
        novoCuidador.setHabilidades("Cuidados especiais com idosos");

        mockMvc.perform(post("/api/cuidadores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoCuidador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Carlos"));
    }

    @Test
    void testBuscarCuidadorPorId() throws Exception {
        mockMvc.perform(get("/api/cuidadores/" + cuidador.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void testAtualizarCuidador() throws Exception {
        cuidador.setNome("José");

        mockMvc.perform(put("/api/cuidadores/" + cuidador.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cuidador)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("José"));
    }

    @Test
    void testExcluirCuidador() throws Exception {
        mockMvc.perform(delete("/api/cuidadores/" + cuidador.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarCuidadores() throws Exception {
        mockMvc.perform(get("/api/cuidadores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"));
    }

    @Test
    void testAtualizarCuidadorNaoEncontrado() throws Exception {
        Cuidador dadosAtualizados = new Cuidador();
        dadosAtualizados.setNome("Carlos");
        dadosAtualizados.setSobrenome("Silva");
        dadosAtualizados.setEmailContato("carlos.silva@example.com"); // Campo obrigatório preenchido
        dadosAtualizados.setTelefoneContato1("(11) 91234-5678");       // Campo obrigatório preenchido
        dadosAtualizados.setPoliticaAceita(true);

        mockMvc.perform(put("/api/cuidadores/999") // ID inexistente
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dadosAtualizados)))
                .andExpect(status().isNotFound());
    }
}