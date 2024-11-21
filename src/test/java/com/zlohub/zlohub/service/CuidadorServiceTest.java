package com.zlohub.zlohub.service;

import com.zlohub.zlohub.model.Cuidador;
import com.zlohub.zlohub.repository.CuidadorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CuidadorServiceTest {

    @Mock
    private CuidadorRepository cuidadorRepository;

    @InjectMocks
    private CuidadorService cuidadorService;

    private Cuidador cuidador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cuidador = new Cuidador();
        cuidador.setId(1L);
        cuidador.setNome("João");
        cuidador.setSobrenome("Silva");
        cuidador.setCpf("123.456.789-00");
        cuidador.setPoliticaAceita(true);
        // Defina outros campos obrigatórios para simular o cuidador completo
    }

    @Test
    void testSalvarCuidador() {
        when(cuidadorRepository.save(cuidador)).thenReturn(cuidador);

        Cuidador savedCuidador = cuidadorService.salvarCuidador(cuidador);
        assertNotNull(savedCuidador);
        assertEquals("João", savedCuidador.getNome());
        verify(cuidadorRepository, times(1)).save(cuidador);
    }

    @Test
    void testListarCuidadores() {
        when(cuidadorRepository.findAll()).thenReturn(List.of(cuidador));

        List<Cuidador> cuidadores = cuidadorService.listarCuidadores();
        assertFalse(cuidadores.isEmpty());
        assertEquals(1, cuidadores.size());
        verify(cuidadorRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(cuidadorRepository.findById(1L)).thenReturn(Optional.of(cuidador));

        Optional<Cuidador> foundCuidador = cuidadorService.buscarPorId(1L);
        assertTrue(foundCuidador.isPresent());
        assertEquals("João", foundCuidador.get().getNome());
        verify(cuidadorRepository, times(1)).findById(1L);
    }

    @Test
    void testAtualizarCuidador() {
        Cuidador updatedCuidador = new Cuidador();
        updatedCuidador.setNome("Carlos");
        updatedCuidador.setSobrenome("Silva");

        when(cuidadorRepository.findById(1L)).thenReturn(Optional.of(cuidador));
        when(cuidadorRepository.save(any(Cuidador.class))).thenReturn(updatedCuidador);

        Cuidador result = cuidadorService.atualizarCuidador(1L, updatedCuidador);
        assertEquals("Carlos", result.getNome());
        verify(cuidadorRepository, times(1)).findById(1L);
        verify(cuidadorRepository, times(1)).save(any(Cuidador.class));
    }

    @Test
    void testExcluirCuidador() {
        doNothing().when(cuidadorRepository).deleteById(1L);

        cuidadorService.excluirCuidador(1L);
        verify(cuidadorRepository, times(1)).deleteById(1L);
    }
}
