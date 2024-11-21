package com.zlohub.zlohub.repository;

import com.zlohub.zlohub.model.Candidatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidaturaRepository extends JpaRepository<Candidatura, Long> {
    List<Candidatura> findByVagaId(Long vagaId);

    List<Candidatura> findByCuidadorId(Long cuidadorId);
}