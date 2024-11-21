package com.zlohub.zlohub.service;

import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VagaService {

    @Autowired
    private VagaRepository vagaRepository;

    public Vaga criarVaga(Vaga vaga) {
        return vagaRepository.save(vaga);
    }

    public List<Vaga> listarVagasPorResponsavel(String cpfResponsavel) {
        return vagaRepository.findByCpfResponsavel(cpfResponsavel);
    }

    public Optional<Vaga> buscarVagaPorId(Long id) {
        return vagaRepository.findById(id);
    }

    public Vaga atualizarVaga(Long id, Vaga vagaAtualizada) {
        return vagaRepository.findById(id)
                .map(vaga -> {
                    if (!vaga.getCpfResponsavel().equals(vagaAtualizada.getCpfResponsavel())) {
                        throw new IllegalArgumentException("CPF do responsável não pode ser alterado.");
                    }

                    vaga.setTitulo(vagaAtualizada.getTitulo());
                    vaga.setDescricao(vagaAtualizada.getDescricao());
                    vaga.setDataHoraInicio(vagaAtualizada.getDataHoraInicio());
                    vaga.setDataHoraFim(vagaAtualizada.getDataHoraFim());
                    vaga.setCidade(vagaAtualizada.getCidade());
                    vaga.setEstado(vagaAtualizada.getEstado());
                    vaga.setTipoDependente(vagaAtualizada.getTipoDependente());
                    vaga.setIdadeDependente(vagaAtualizada.getIdadeDependente());
                    vaga.setDoencaDiagnosticada(vagaAtualizada.getDoencaDiagnosticada());
                    vaga.setStatus(vagaAtualizada.getStatus());

                    return vagaRepository.save(vaga);
                })
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada!"));
    }

    public void excluirVaga(Long id) {
        Vaga vaga = vagaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaga não encontrada."));
        vagaRepository.deleteById(vaga.getId());
    }

    public List<VagaDTO> listarTodasAsVagas() {
        List<Vaga> vagas = vagaRepository.findAll();
        return vagas.stream().map(this::converterParaDTO).toList();
    }

    private VagaDTO converterParaDTO(Vaga vaga) {
        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setId(vaga.getId());
        vagaDTO.setCpfResponsavel(vaga.getCpfResponsavel());
        vagaDTO.setTitulo(vaga.getTitulo());
        vagaDTO.setDescricao(vaga.getDescricao());
        vagaDTO.setCidade(vaga.getCidade());
        vagaDTO.setEstado(vaga.getEstado());
        vagaDTO.setTipoDependente(vaga.getTipoDependente().toString());
        vagaDTO.setIdadeDependente(vaga.getIdadeDependente());
        vagaDTO.setDoencaDiagnosticada(vaga.getDoencaDiagnosticada());
        vagaDTO.setStatus(vaga.getStatus().toString());
        return vagaDTO;
    }
}
