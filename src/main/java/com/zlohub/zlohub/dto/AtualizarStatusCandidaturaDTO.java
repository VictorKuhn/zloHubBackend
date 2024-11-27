package com.zlohub.zlohub.dto;

import com.zlohub.zlohub.model.StatusCandidatura;
import lombok.Data;

@Data
public class AtualizarStatusCandidaturaDTO {
    private Long id;
    private StatusCandidatura status;
}