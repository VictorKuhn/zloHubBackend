package com.zlohub.zlohub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "candidaturas")
public class Candidatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vaga_id", nullable = false)
    private Vaga vaga;

    @NotNull
    @Column(name = "cuidador_id", nullable = false)
    private Long cuidadorId;

    @NotNull
    @Column(name = "valor_hora", nullable = false)
    private Double valorHora;

    @Column(name = "mensagem_envio")
    private String mensagemEnvio;

    @NotNull
    @Column(name = "politica_privacidade", nullable = false)
    private Boolean politicaPrivacidade;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusCandidatura status; // Novo campo
}
