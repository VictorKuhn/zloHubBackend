package com.zlohub.zlohub.service;

import com.zlohub.zlohub.dto.AtualizarStatusCandidaturaDTO;
import com.zlohub.zlohub.dto.CandidaturaDTO;
import com.zlohub.zlohub.model.StatusCandidatura;
import com.zlohub.zlohub.model.Candidatura;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.repository.CandidaturaRepository;
import com.zlohub.zlohub.repository.VagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CandidaturaServiceTest {

    @InjectMocks
    private CandidaturaService candidaturaService;

    @Mock
    private CandidaturaRepository candidaturaRepository;

    @Mock
    private VagaRepository vagaRepository;

    private Candidatura candidatura;
    private Vaga vaga;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vaga = new Vaga();
        vaga.setId(1L);
        vaga.setCpfResponsavel("12345678901");

        candidatura = new Candidatura();
        candidatura.setId(1L);
        candidatura.setVaga(vaga);
        candidatura.setCuidadorId(69L);
        candidatura.setValorHora(50.0);
        candidatura.setMensagemEnvio("Tenho experiência.");
        candidatura.setPoliticaPrivacidade(true);
        candidatura.setStatus(StatusCandidatura.AGUARDANDO); // Status inicial
    }

    @Test
    void criarCandidatura_DeveCriarComStatusAguardandoQuandoVagaExiste() {
        // Arrange
        when(vagaRepository.findById(vaga.getId())).thenReturn(Optional.of(vaga));
        when(candidaturaRepository.save(candidatura)).thenReturn(candidatura);

        // Act
        CandidaturaDTO result = candidaturaService.criarCandidatura(candidatura);

        // Assert
        assertNotNull(result);
        assertEquals(candidatura.getId(), result.getId());
        assertEquals("AGUARDANDO", result.getStatus()); // Verifica o status inicial
        verify(candidaturaRepository, times(1)).save(candidatura);
    }

    @Test
    void criarCandidatura_DeveLancarExcecaoQuandoVagaNaoExiste() {
        // Arrange
        when(vagaRepository.findById(vaga.getId())).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> candidaturaService.criarCandidatura(candidatura));

        assertEquals("Vaga não encontrada.", exception.getMessage());
        verify(candidaturaRepository, never()).save(any());
    }

    @Test
    void listarCandidaturasPorVaga_DeveRetornarLista() {
        // Arrange
        when(candidaturaRepository.findByVagaId(vaga.getId())).thenReturn(List.of(candidatura));

        // Act
        List<CandidaturaDTO> result = candidaturaService.listarCandidaturasPorVaga(vaga.getId());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AGUARDANDO", result.get(0).getStatus()); // Verifica o status
        verify(candidaturaRepository, times(1)).findByVagaId(vaga.getId());
    }

    @Test
    void listarCandidaturasPorCuidador_DeveRetornarLista() {
        // Arrange
        when(candidaturaRepository.findByCuidadorId(candidatura.getCuidadorId())).thenReturn(List.of(candidatura));

        // Act
        List<CandidaturaDTO> result = candidaturaService.listarCandidaturasPorCuidador(candidatura.getCuidadorId());

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("AGUARDANDO", result.get(0).getStatus()); // Verifica o status
        verify(candidaturaRepository, times(1)).findByCuidadorId(candidatura.getCuidadorId());
    }

    @Test
    void excluirCandidatura_DeveExcluirQuandoExiste() {
        // Arrange
        when(candidaturaRepository.existsById(candidatura.getId())).thenReturn(true);

        // Act & Assert
        assertDoesNotThrow(() -> candidaturaService.excluirCandidatura(candidatura.getId()));
        verify(candidaturaRepository, times(1)).deleteById(candidatura.getId());
    }

    @Test
    void excluirCandidatura_DeveLancarExcecaoQuandoNaoExiste() {
        // Arrange
        when(candidaturaRepository.existsById(candidatura.getId())).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> candidaturaService.excluirCandidatura(candidatura.getId()));

        assertEquals("Candidatura não encontrada.", exception.getMessage());
        verify(candidaturaRepository, never()).deleteById(any());
    }

    @Test
    public void atualizarStatus_Sucesso() {
        AtualizarStatusCandidaturaDTO atualizarStatusDTO = new AtualizarStatusCandidaturaDTO();
        atualizarStatusDTO.setId(1L);
        atualizarStatusDTO.setStatus(StatusCandidatura.ACEITO);

        // Cria uma vaga mock para associar à candidatura
        Vaga vaga = new Vaga();
        vaga.setId(1L);
        vaga.setTitulo("Vaga de teste");

        // Configura a candidatura mock
        Candidatura candidatura = new Candidatura();
        candidatura.setId(1L);
        candidatura.setStatus(StatusCandidatura.AGUARDANDO);
        candidatura.setVaga(vaga); // Adiciona a vaga ao mock

        when(candidaturaRepository.findById(1L)).thenReturn(Optional.of(candidatura));
        when(candidaturaRepository.save(any(Candidatura.class))).thenAnswer(i -> i.getArgument(0));

        CandidaturaDTO resultado = candidaturaService.atualizarStatus(atualizarStatusDTO);

        // Verifica o resultado
        assertEquals(StatusCandidatura.ACEITO.name(), resultado.getStatus());
        assertEquals(1L, resultado.getVaga().getId());
        verify(candidaturaRepository, times(1)).findById(1L);
        verify(candidaturaRepository, times(1)).save(any(Candidatura.class));
    }

    @Test
    public void atualizarStatus_CandidaturaNaoEncontrada() {
        AtualizarStatusCandidaturaDTO atualizarStatusDTO = new AtualizarStatusCandidaturaDTO();
        atualizarStatusDTO.setId(1L);
        atualizarStatusDTO.setStatus(StatusCandidatura.ACEITO);

        when(candidaturaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> candidaturaService.atualizarStatus(atualizarStatusDTO));

        assertEquals("Candidatura não encontrada.", exception.getMessage());
        verify(candidaturaRepository, times(1)).findById(1L);
        verify(candidaturaRepository, never()).save(any(Candidatura.class));
    }
}
