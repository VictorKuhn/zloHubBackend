package com.zlohub.zlohub.service;

import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.model.StatusVaga;
import com.zlohub.zlohub.model.TipoDependente;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.repository.VagaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VagaServiceTest {

    @Mock
    private VagaRepository vagaRepository;

    @InjectMocks
    private VagaService vagaService;

    private Vaga vaga1;
    private Vaga vaga2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vaga1 = new Vaga();
        vaga1.setId(1L);
        vaga1.setCpfResponsavel("10020030085");
        vaga1.setTitulo("Acompanhante para Idoso");
        vaga1.setDescricao("Cuidar de idoso com Alzheimer");
        vaga1.setDataHoraInicio(LocalDateTime.of(2024, 11, 25, 8, 0));
        vaga1.setDataHoraFim(LocalDateTime.of(2024, 11, 25, 18, 0));
        vaga1.setCidade("Joinville");
        vaga1.setEstado("SC");
        vaga1.setTipoDependente(TipoDependente.IDOSO);
        vaga1.setIdadeDependente(75);
        vaga1.setDoencaDiagnosticada("Alzheimer");
        vaga1.setStatus(StatusVaga.ATIVA);

        vaga2 = new Vaga();
        vaga2.setId(2L);
        vaga2.setCpfResponsavel("20030040095");
        vaga2.setTitulo("Cuidador Infantil");
        vaga2.setDescricao("Cuidar de criança com TEA");
        vaga2.setDataHoraInicio(LocalDateTime.of(2024, 12, 1, 8, 0));
        vaga2.setDataHoraFim(LocalDateTime.of(2024, 12, 1, 18, 0));
        vaga2.setCidade("Florianópolis");
        vaga2.setEstado("SC");
        vaga2.setTipoDependente(TipoDependente.CRIANCA);
        vaga2.setIdadeDependente(10);
        vaga2.setDoencaDiagnosticada("TEA");
        vaga2.setStatus(StatusVaga.ATIVA);
    }

    @Test
    void criarVaga_DeveSalvarEVagaRetornada() {
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vaga1);

        Vaga novaVaga = vagaService.criarVaga(vaga1);

        assertNotNull(novaVaga);
        assertEquals(vaga1.getTitulo(), novaVaga.getTitulo());
        verify(vagaRepository, times(1)).save(vaga1);
    }

    @Test
    void listarVagasPorResponsavel_DeveRetornarListaDeVagas() {
        when(vagaRepository.findByCpfResponsavel("10020030085")).thenReturn(Arrays.asList(vaga1));

        List<Vaga> vagas = vagaService.listarVagasPorResponsavel("10020030085");

        assertEquals(1, vagas.size());
        assertEquals("10020030085", vagas.get(0).getCpfResponsavel());
        verify(vagaRepository, times(1)).findByCpfResponsavel("10020030085");
    }

    @Test
    void buscarVagaPorId_DeveRetornarVagaQuandoEncontrada() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga1));

        Optional<Vaga> vagaEncontrada = vagaService.buscarVagaPorId(1L);

        assertTrue(vagaEncontrada.isPresent());
        assertEquals("Acompanhante para Idoso", vagaEncontrada.get().getTitulo());
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    void buscarVagaPorId_DeveRetornarVazioQuandoNaoEncontrada() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Vaga> vagaEncontrada = vagaService.buscarVagaPorId(1L);

        assertFalse(vagaEncontrada.isPresent());
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    void atualizarVaga_DeveAtualizarQuandoEncontrada() {
        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setTitulo("Novo Título");
        vagaAtualizada.setCpfResponsavel("10020030085");

        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga1));
        when(vagaRepository.save(any(Vaga.class))).thenReturn(vagaAtualizada);

        Vaga vagaRetornada = vagaService.atualizarVaga(1L, vagaAtualizada);

        assertNotNull(vagaRetornada);
        assertEquals("Novo Título", vagaRetornada.getTitulo());
        verify(vagaRepository, times(1)).findById(1L);
        verify(vagaRepository, times(1)).save(any(Vaga.class));
    }

    @Test
    void atualizarVaga_DeveLancarExcecaoQuandoCpfAlterado() {
        Vaga vagaAtualizada = new Vaga();
        vagaAtualizada.setCpfResponsavel("99999999999");

        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga1));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                vagaService.atualizarVaga(1L, vagaAtualizada));

        assertEquals("CPF do responsável não pode ser alterado.", exception.getMessage());
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    void excluirVaga_DeveExcluirQuandoEncontrada() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.of(vaga1));
        doNothing().when(vagaRepository).deleteById(1L);

        assertDoesNotThrow(() -> vagaService.excluirVaga(1L));

        verify(vagaRepository, times(1)).findById(1L);
        verify(vagaRepository, times(1)).deleteById(1L);
    }

    @Test
    void excluirVaga_DeveLancarExcecaoQuandoNaoEncontrada() {
        when(vagaRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                vagaService.excluirVaga(1L));

        assertEquals("Vaga não encontrada.", exception.getMessage());
        verify(vagaRepository, times(1)).findById(1L);
    }

    @Test
    void listarTodasAsVagas_DeveRetornarListaDeVagaDTO() {
        when(vagaRepository.findAll()).thenReturn(List.of(vaga1, vaga2));

        List<VagaDTO> resultado = vagaService.listarTodasAsVagas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        VagaDTO vagaDTO1 = resultado.get(0);
        assertEquals(vaga1.getId(), vagaDTO1.getId());
        assertEquals(vaga1.getCpfResponsavel(), vagaDTO1.getCpfResponsavel());
        assertEquals(vaga1.getTitulo(), vagaDTO1.getTitulo());

        VagaDTO vagaDTO2 = resultado.get(1);
        assertEquals(vaga2.getId(), vagaDTO2.getId());
        assertEquals(vaga2.getCpfResponsavel(), vagaDTO2.getCpfResponsavel());
        assertEquals(vaga2.getTitulo(), vagaDTO2.getTitulo());

        verify(vagaRepository, times(1)).findAll();
    }
}