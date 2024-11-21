package com.zlohub.zlohub.repository;

import com.zlohub.zlohub.model.Vaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VagaRepository extends JpaRepository<Vaga, Long> {
    List<Vaga> findByCpfResponsavel(String cpfResponsavel);
}