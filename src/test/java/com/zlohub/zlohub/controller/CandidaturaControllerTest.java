package com.zlohub.zlohub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlohub.zlohub.dto.CandidaturaDTO;
import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.dto.AtualizarStatusCandidaturaDTO;
import com.zlohub.zlohub.model.StatusCandidatura;
import com.zlohub.zlohub.service.CandidaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CandidaturaController.class)
class CandidaturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidaturaService candidaturaService;

    @Autowired
    private ObjectMapper objectMapper;

    private CandidaturaDTO candidaturaDTO;

    @BeforeEach
    void setUp() {
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setId(1L);

        candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(1L);
        candidaturaDTO.setCuidadorId(69L);
        candidaturaDTO.setValorHora(50.0);
        candidaturaDTO.setMensagemEnvio("Tenho experiência.");
        candidaturaDTO.setPoliticaPrivacidade(true);
        candidaturaDTO.setVaga(vagaDTO);
        candidaturaDTO.setStatus(String.valueOf(StatusCandidatura.AGUARDANDO)); // Status inicial
    }

    @Test
    void criarCandidatura_DeveRetornar201QuandoCriada() throws Exception {
        when(candidaturaService.criarCandidatura(Mockito.any(com.zlohub.zlohub.model.Candidatura.class))).thenReturn(candidaturaDTO);

        mockMvc.perform(post("/api/candidaturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(candidaturaDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cuidadorId").value(69))
                .andExpect(jsonPath("$.valorHora").value(50.0))
                .andExpect(jsonPath("$.status").value("AGUARDANDO")); // Verifica o status

        verify(candidaturaService, times(1)).criarCandidatura(any());
    }

    @Test
    void listarCandidaturasPorVaga_DeveRetornarListaQuandoExistir() throws Exception {
        List<CandidaturaDTO> candidaturas = List.of(candidaturaDTO);
        when(candidaturaService.listarCandidaturasPorVaga(1L)).thenReturn(candidaturas);

        mockMvc.perform(get("/api/candidaturas/vaga/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].valorHora").value(50.0))
                .andExpect(jsonPath("$[0].status").value("AGUARDANDO")); // Verifica o status

        verify(candidaturaService, times(1)).listarCandidaturasPorVaga(1L);
    }

    @Test
    void listarCandidaturasPorVaga_DeveRetornarVazioQuandoNaoExistir() throws Exception {
        when(candidaturaService.listarCandidaturasPorVaga(1L)).thenReturn(List.of());

        mockMvc.perform(get("/api/candidaturas/vaga/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(candidaturaService, times(1)).listarCandidaturasPorVaga(1L);
    }

    @Test
    void excluirCandidatura_DeveRetornar204QuandoExcluida() throws Exception {
        doNothing().when(candidaturaService).excluirCandidatura(1L);

        mockMvc.perform(delete("/api/candidaturas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(candidaturaService, times(1)).excluirCandidatura(1L);
    }

    @Test
    void excluirCandidatura_DeveRetornar404QuandoNaoEncontrada() throws Exception {
        doThrow(new RuntimeException("Candidatura não encontrada.")).when(candidaturaService).excluirCandidatura(1L);

        mockMvc.perform(delete("/api/candidaturas/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(candidaturaService, times(1)).excluirCandidatura(1L);
    }

    @Test
    void listarCandidaturasPorCuidador_DeveRetornarListaQuandoExistir() throws Exception {
        List<CandidaturaDTO> candidaturas = List.of(candidaturaDTO);
        when(candidaturaService.listarCandidaturasPorCuidador(69L)).thenReturn(candidaturas);

        mockMvc.perform(get("/api/candidaturas/cuidador/69")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].valorHora").value(50.0))
                .andExpect(jsonPath("$[0].vaga.id").value(1))
                .andExpect(jsonPath("$[0].status").value("AGUARDANDO")); // Verifica o status

        verify(candidaturaService, times(1)).listarCandidaturasPorCuidador(69L);
    }

    @Test
    public void atualizarStatus_Sucesso() throws Exception {
        AtualizarStatusCandidaturaDTO atualizarStatusDTO = new AtualizarStatusCandidaturaDTO();
        atualizarStatusDTO.setId(1L);
        atualizarStatusDTO.setStatus(StatusCandidatura.ACEITO);

        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(1L);
        candidaturaDTO.setStatus(StatusCandidatura.ACEITO.name());

        when(candidaturaService.atualizarStatus(any(AtualizarStatusCandidaturaDTO.class)))
                .thenReturn(candidaturaDTO);

        mockMvc.perform(patch("/api/candidaturas/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(atualizarStatusDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("ACEITO"));

        verify(candidaturaService, times(1)).atualizarStatus(any(AtualizarStatusCandidaturaDTO.class));
    }

    @Test
    public void atualizarStatus_CandidaturaNaoEncontrada() throws Exception {
        AtualizarStatusCandidaturaDTO atualizarStatusDTO = new AtualizarStatusCandidaturaDTO();
        atualizarStatusDTO.setId(1L);
        atualizarStatusDTO.setStatus(StatusCandidatura.ACEITO);

        when(candidaturaService.atualizarStatus(any(AtualizarStatusCandidaturaDTO.class)))
                .thenThrow(new RuntimeException("Candidatura não encontrada."));

        mockMvc.perform(patch("/api/candidaturas/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(atualizarStatusDTO)))
                .andExpect(status().isBadRequest());

        verify(candidaturaService, times(1)).atualizarStatus(any(AtualizarStatusCandidaturaDTO.class));
    }

}
