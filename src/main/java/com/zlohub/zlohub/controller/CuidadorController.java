package com.zlohub.zlohub.controller;

import com.zlohub.zlohub.model.Cuidador;
import com.zlohub.zlohub.service.CuidadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cuidadores")
public class CuidadorController {

    @Autowired
    private CuidadorService cuidadorService;

    @PostMapping
    public ResponseEntity<Cuidador> cadastrarCuidador(@Valid @RequestBody Cuidador cuidador) {
        Cuidador novoCuidador = cuidadorService.salvarCuidador(cuidador);
        return ResponseEntity.ok(novoCuidador);
    }

    @PostMapping("/buscar-por-email")
    public ResponseEntity<Cuidador> buscarPorEmail(@RequestBody Map<String, String> requestBody) {
        String email = requestBody.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return cuidadorService.buscarPorEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping
    public List<Cuidador> listarCuidadores() {
        return cuidadorService.listarCuidadores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cuidador> buscarCuidador(@PathVariable Long id) {
        return cuidadorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuidador> atualizarCuidador(@PathVariable Long id, @RequestBody Cuidador dadosAtualizados) {
        if (!cuidadorService.buscarPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        try {
            Cuidador cuidadorAtualizado = cuidadorService.atualizarCuidador(id, dadosAtualizados);
            return ResponseEntity.ok(cuidadorAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirCuidador(@PathVariable Long id) {
        cuidadorService.excluirCuidador(id);
        return ResponseEntity.noContent().build();
    }
}
