package com.zlohub.zlohub.dto;

import lombok.Data;

@Data
public class CandidaturaDTO {
    private Long id;
    private Long cuidadorId;
    private Double valorHora;
    private String mensagemEnvio;
    private Boolean politicaPrivacidade;
    private VagaDTO vaga;
    private String status;
}