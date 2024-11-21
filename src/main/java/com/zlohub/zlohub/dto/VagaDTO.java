package com.zlohub.zlohub.dto;

import lombok.Data;

@Data
public class VagaDTO {
    private Long id;
    private String cpfResponsavel;
    private String titulo;
    private String descricao;
    private String cidade;
    private String estado;
    private String tipoDependente;
    private Integer idadeDependente;
    private String doencaDiagnosticada;
    private String status;
}