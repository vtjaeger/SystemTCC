package com.tcc.dtos.request.aluno;

import jakarta.validation.constraints.NotBlank;

public record AlunoRequest(
        @NotBlank
        String nome) {
}
