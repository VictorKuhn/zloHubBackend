package com.zlohub.zlohub.controller;

import com.zlohub.zlohub.dto.VagaDTO;
import com.zlohub.zlohub.model.Vaga;
import com.zlohub.zlohub.service.VagaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vagas")
public class VagaController {

    @Autowired
    private VagaService vagaService;

    public VagaController(VagaService vagaService) {
        this.vagaService = vagaService;
    }

    // Criar uma nova vaga
    @PostMapping
    public ResponseEntity<Vaga> criarVaga(@RequestBody Vaga vaga) {
        Vaga novaVaga = vagaService.criarVaga(vaga);
        return ResponseEntity.ok(novaVaga);
    }

    // Listar vagas por CPF do responsável
    @GetMapping("/responsavel/{cpfResponsavel}") // Corrigido para diferenciar do endpoint por ID
    public ResponseEntity<List<Vaga>> listarVagasPorResponsavel(@PathVariable String cpfResponsavel) {
        List<Vaga> vagas = vagaService.listarVagasPorResponsavel(cpfResponsavel);
        return ResponseEntity.ok(vagas);
    }

    // Buscar vaga por ID
    @GetMapping("/id/{id}") // Corrigido para diferenciar do endpoint por CPF
    public ResponseEntity<Vaga> buscarVagaPorId(@PathVariable Long id) {
        return vagaService.buscarVagaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar uma vaga por ID
    @PutMapping("/{id}")
    public ResponseEntity<Vaga> atualizarVaga(@PathVariable Long id, @RequestBody Vaga vagaAtualizada) {
        try {
            Vaga vaga = vagaService.atualizarVaga(id, vagaAtualizada);
            return ResponseEntity.ok(vaga);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Erro 400 se CPF for alterado
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build(); // Erro 404 se a vaga não for encontrada
        }
    }

    // Excluir uma vaga por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirVaga(@PathVariable Long id) {
        try {
            vagaService.excluirVaga(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Listar todas as vagas
    @GetMapping
    public ResponseEntity<List<VagaDTO>> listarTodasAsVagas() {
        List<VagaDTO> vagas = vagaService.listarTodasAsVagas();
        return ResponseEntity.ok(vagas);
    }
}
