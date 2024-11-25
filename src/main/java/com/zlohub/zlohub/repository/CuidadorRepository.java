package com.zlohub.zlohub.repository;

import com.zlohub.zlohub.model.Cuidador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CuidadorRepository extends JpaRepository<Cuidador, Long> {
    Optional<Cuidador> findByEmailContato(String emailContato);
}
