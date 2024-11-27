package com.zlohub.zlohub.service;

import com.zlohub.zlohub.dto.AtualizarStatusCandidaturaDTO;
import com.zlohub.zlohub.dto.CandidaturaDTO;
import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.model.StatusCandidatura;
import com.zlohub.zlohub.model.Candidatura;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.repository.CandidaturaRepository;
import com.zlohub.zlohub.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CandidaturaService {

    @Autowired
    private CandidaturaRepository candidaturaRepository;

    @Autowired
    private VagaRepository vagaRepository;

    public CandidaturaDTO criarCandidatura(Candidatura candidatura) {
        // Verifica se a vaga existe
        Optional<Vaga> vaga = vagaRepository.findById(candidatura.getVaga().getId());
        if (vaga.isEmpty()) {
            throw new RuntimeException("Vaga não encontrada.");
        }

        // Define o status inicial como AGUARDANDO
        candidatura.setStatus(StatusCandidatura.AGUARDANDO);

        // Salva a candidatura no banco
        Candidatura novaCandidatura = candidaturaRepository.save(candidatura);

        // Converte a entidade salva para DTO e retorna
        return converterParaDTO(novaCandidatura);
    }

    public List<CandidaturaDTO> listarCandidaturasPorVaga(Long vagaId) {
        List<Candidatura> candidaturas = candidaturaRepository.findByVagaId(vagaId);
        return candidaturas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public List<CandidaturaDTO> listarCandidaturasPorCuidador(Long cuidadorId) {
        List<Candidatura> candidaturas = candidaturaRepository.findByCuidadorId(cuidadorId);
        return candidaturas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public void excluirCandidatura(Long id) {
        if (!candidaturaRepository.existsById(id)) {
            throw new RuntimeException("Candidatura não encontrada.");
        }
        candidaturaRepository.deleteById(id);
    }

    public CandidaturaDTO atualizarStatus(AtualizarStatusCandidaturaDTO atualizarStatusDTO) {
        // Verifica se a candidatura existe
        Candidatura candidatura = candidaturaRepository.findById(atualizarStatusDTO.getId())
                .orElseThrow(() -> new RuntimeException("Candidatura não encontrada."));

        // Atualiza o status
        candidatura.setStatus(atualizarStatusDTO.getStatus());
        Candidatura candidaturaAtualizada = candidaturaRepository.save(candidatura);

        // Retorna o DTO atualizado
        return converterParaDTO(candidaturaAtualizada);
    }

    private CandidaturaDTO converterParaDTO(Candidatura candidatura) {
        Vaga vaga = candidatura.getVaga();

        VagaDTO vagaDTO = new VagaDTO();
        vagaDTO.setId(vaga.getId());
        vagaDTO.setCpfResponsavel(vaga.getCpfResponsavel());
        vagaDTO.setTitulo(vaga.getTitulo());
        vagaDTO.setDescricao(vaga.getDescricao());
        vagaDTO.setCidade(vaga.getCidade());
        vagaDTO.setEstado(vaga.getEstado());
        vagaDTO.setTipoDependente(String.valueOf(vaga.getTipoDependente()));
        vagaDTO.setIdadeDependente(vaga.getIdadeDependente());
        vagaDTO.setDoencaDiagnosticada(vaga.getDoencaDiagnosticada());
        vagaDTO.setStatus(String.valueOf(vaga.getStatus()));

        CandidaturaDTO candidaturaDTO = new CandidaturaDTO();
        candidaturaDTO.setId(candidatura.getId());
        candidaturaDTO.setCuidadorId(candidatura.getCuidadorId());
        candidaturaDTO.setValorHora(candidatura.getValorHora());
        candidaturaDTO.setMensagemEnvio(candidatura.getMensagemEnvio());
        candidaturaDTO.setPoliticaPrivacidade(candidatura.getPoliticaPrivacidade());
        candidaturaDTO.setVaga(vagaDTO);

        // Adiciona o status da candidatura ao DTO
        candidaturaDTO.setStatus(String.valueOf(candidatura.getStatus()));

        return candidaturaDTO;
    }
}