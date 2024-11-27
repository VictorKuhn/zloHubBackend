package com.zlohub.zlohub.controller;

import com.zlohub.zlohub.dto.AtualizarStatusCandidaturaDTO;
import com.zlohub.zlohub.dto.CandidaturaDTO;
import com.zlohub.zlohub.model.Candidatura;
import com.zlohub.zlohub.service.CandidaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidaturas")
public class CandidaturaController {

    @Autowired
    private CandidaturaService candidaturaService;

    @PostMapping
    public ResponseEntity<CandidaturaDTO> criarCandidatura(@RequestBody Candidatura candidatura) {
        CandidaturaDTO novaCandidatura = candidaturaService.criarCandidatura(candidatura);
        return ResponseEntity.ok(novaCandidatura);
    }

    @GetMapping("/vaga/{vagaId}")
    public ResponseEntity<List<CandidaturaDTO>> listarCandidaturasPorVaga(@PathVariable Long vagaId) {
        List<CandidaturaDTO> candidaturas = candidaturaService.listarCandidaturasPorVaga(vagaId);
        return ResponseEntity.ok(candidaturas);
    }

    @GetMapping("/cuidador/{cuidadorId}")
    public ResponseEntity<List<CandidaturaDTO>> listarCandidaturasPorCuidador(@PathVariable Long cuidadorId) {
        List<CandidaturaDTO> candidaturas = candidaturaService.listarCandidaturasPorCuidador(cuidadorId);
        return ResponseEntity.ok(candidaturas);
    }

    @PatchMapping("/status")
    public ResponseEntity<CandidaturaDTO> atualizarStatus(@RequestBody AtualizarStatusCandidaturaDTO atualizarStatusDTO) {
        try {
            CandidaturaDTO candidaturaAtualizada = candidaturaService.atualizarStatus(atualizarStatusDTO);
            return ResponseEntity.ok(candidaturaAtualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCandidatura(@PathVariable Long id) {
        try {
            candidaturaService.excluirCandidatura(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
