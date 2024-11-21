package com.zlohub.zlohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.model.StatusVaga;
import com.zlohub.zlohub.model.TipoDependente;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.service.VagaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VagaController.class)
class VagaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VagaService vagaService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vaga vaga;

    @BeforeEach
    void setUp() {
        vaga = new Vaga();
        vaga.setId(1L);
        vaga.setCpfResponsavel("10020030082");
        vaga.setTitulo("Cuidado com idoso");
        vaga.setDescricao("Cuidador para idoso acamado.");
        vaga.setDataHoraInicio(LocalDateTime.now());
        vaga.setDataHoraFim(LocalDateTime.now().plusHours(4));
        vaga.setCidade("Joinville");
        vaga.setEstado("SC");
        vaga.setTipoDependente(TipoDependente.valueOf("IDOSO"));
        vaga.setIdadeDependente(80);
        vaga.setDoencaDiagnosticada("Alzheimer");
        vaga.setStatus(StatusVaga.valueOf("ATIVA"));
    }

    @Test
    void criarVaga_DeveRetornarVagaCriada() throws Exception {
        when(vagaService.criarVaga(Mockito.any(Vaga.class))).thenReturn(vaga);

        mockMvc.perform(post("/api/vagas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vaga)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cpfResponsavel", is("10020030082")))
                .andExpect(jsonPath("$.titulo", is("Cuidado com idoso")))
                .andExpect(jsonPath("$.descricao", is("Cuidador para idoso acamado.")));

        verify(vagaService, times(1)).criarVaga(Mockito.any(Vaga.class));
    }

    @Test
    void listarVagasPorResponsavel_DeveRetornarListaDeVagas() throws Exception {
        when(vagaService.listarVagasPorResponsavel("10020030082"))
                .thenReturn(Arrays.asList(vaga, vaga));

        mockMvc.perform(get("/api/vagas/responsavel/10020030082") // Caminho corrigido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].cpfResponsavel", is("10020030082")))
                .andExpect(jsonPath("$[1].cpfResponsavel", is("10020030082")));

        verify(vagaService, times(1)).listarVagasPorResponsavel("10020030082");
    }

    @Test
    void buscarVagaPorId_DeveRetornarVaga() throws Exception {
        when(vagaService.buscarVagaPorId(1L)).thenReturn(Optional.of(vaga));

        mockMvc.perform(get("/api/vagas/id/1") // Caminho corrigido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.cpfResponsavel", is("10020030082")))
                .andExpect(jsonPath("$.titulo", is("Cuidado com idoso")));

        verify(vagaService, times(1)).buscarVagaPorId(1L);
    }

    @Test
    void buscarVagaPorId_DeveRetornar404QuandoNaoEncontrada() throws Exception {
        when(vagaService.buscarVagaPorId(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vagas/id/1") // Caminho corrigido
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(vagaService, times(1)).buscarVagaPorId(1L);
    }

    @Test
    void atualizarVaga_DeveAtualizarVaga() throws Exception {
        Vaga vagaAtualizada = vaga;
        vagaAtualizada.setDescricao("Cuidador para idoso em cadeira de rodas.");

        when(vagaService.atualizarVaga(eq(1L), Mockito.any(Vaga.class))).thenReturn(vagaAtualizada);

        mockMvc.perform(put("/api/vagas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vagaAtualizada)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descricao", is("Cuidador para idoso em cadeira de rodas.")));

        verify(vagaService, times(1)).atualizarVaga(eq(1L), Mockito.any(Vaga.class));
    }

    @Test
    void excluirVaga_DeveExcluirVaga() throws Exception {
        doNothing().when(vagaService).excluirVaga(1L);

        mockMvc.perform(delete("/api/vagas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(vagaService, times(1)).excluirVaga(1L);
    }

    @Test
    void excluirVaga_DeveRetornar404QuandoNaoEncontrada() throws Exception {
        doThrow(new RuntimeException("Vaga não encontrada.")).when(vagaService).excluirVaga(1L);

        mockMvc.perform(delete("/api/vagas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(vagaService, times(1)).excluirVaga(1L);
    }

    @Test
    void atualizarVaga_DeveRetornarBadRequestQuandoCpfAlterado() throws Exception {
        // Dados de teste
        Long id = 1L;
        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setCpfResponsavel("12345678901"); // CPF alterado
        vagaAtualizada.setTitulo("Nova Vaga Atualizada");

        // Mock para lançar a exceção IllegalArgumentException
        Mockito.when(vagaService.atualizarVaga(eq(id), any(Vaga.class)))
                .thenThrow(new IllegalArgumentException("CPF do responsável não pode ser alterado."));

        // Executa a requisição PUT e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.put("/api/vagas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vagaAtualizada)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("")); // Corpo vazio
    }

    @Test
    void atualizarVaga_DeveRetornarNotFoundQuandoVagaNaoEncontrada() throws Exception {
        // Dados de teste
        Long id = 99L; // ID de vaga inexistente
        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setTitulo("Vaga Inexistente");

        // Mock para lançar a exceção RuntimeException
        Mockito.when(vagaService.atualizarVaga(eq(id), any(Vaga.class)))
                .thenThrow(new RuntimeException("Vaga não encontrada."));

        // Executa a requisição PUT e verifica o resultado
        mockMvc.perform(MockMvcRequestBuilders.put("/api/vagas/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(vagaAtualizada)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void listarTodasAsVagas_DeveRetornarListaDeVagas() throws Exception {
        VagaDTO vagaDTO1 = new VagaDTO();
        vagaDTO1.setId(1L);
        vagaDTO1.setCpfResponsavel("12345678901");
        vagaDTO1.setTitulo("Vaga de Cuidador de Idosos");
        vagaDTO1.setDescricao("Cuidar de idoso com necessidades especiais.");
        vagaDTO1.setCidade("São Paulo");
        vagaDTO1.setEstado("SP");
        vagaDTO1.setTipoDependente("IDOSO");
        vagaDTO1.setIdadeDependente(70);
        vagaDTO1.setDoencaDiagnosticada("Diabetes");
        vagaDTO1.setStatus("ATIVA");

        VagaDTO vagaDTO2 = new VagaDTO();
        vagaDTO2.setId(2L);
        vagaDTO2.setCpfResponsavel("98765432100");
        vagaDTO2.setTitulo("Cuidador Infantil");
        vagaDTO2.setDescricao("Responsável por cuidar de uma criança com TEA.");
        vagaDTO2.setCidade("Rio de Janeiro");
        vagaDTO2.setEstado("RJ");
        vagaDTO2.setTipoDependente("CRIANCA");
        vagaDTO2.setIdadeDependente(10);
        vagaDTO2.setDoencaDiagnosticada("TEA");
        vagaDTO2.setStatus("ENCERRADA");

        List<VagaDTO> vagas = List.of(vagaDTO1, vagaDTO2);

        when(vagaService.listarTodasAsVagas()).thenReturn(vagas);

        mockMvc.perform(get("/api/vagas")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].titulo").value("Cuidador Infantil"));

        verify(vagaService, times(1)).listarTodasAsVagas();
    }
}
