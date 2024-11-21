package com.zlohub.zlohub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "vagas")
public class Vaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cpfResponsavel;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(nullable = false, length = 1000)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHoraInicio;

    @Column(nullable = false)
    private LocalDateTime dataHoraFim;

    @Column(nullable = false, length = 100)
    private String cidade;

    @Column(nullable = false, length = 2)
    private String estado;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDependente tipoDependente;

    @Column(nullable = false)
    private Integer idadeDependente;

    @Column(length = 200)
    private String doencaDiagnosticada;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVaga status;

    // Getters e Setters
    // Construtores
}
