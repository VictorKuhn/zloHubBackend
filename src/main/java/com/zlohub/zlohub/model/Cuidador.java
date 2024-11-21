package com.zlohub.zlohub.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class Cuidador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nome;

    @NotNull
    private String sobrenome;

    @NotNull
    private String cpf;

    private LocalDate dataNascimento;
    private String logradouro;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;
    private String complemento;

    @NotNull
    private String telefoneContato1;
    private String telefoneContato2;

    @Email
    @NotNull
    private String emailContato;

    private int tempoExperiencia;
    private String formacao;
    private String habilidades;

    private String referencia1;
    private String telefoneReferencia1;
    private String referencia2;
    private String telefoneReferencia2;
    private String referencia3;
    private String telefoneReferencia3;

    @NotNull
    private Boolean politicaAceita;
}
