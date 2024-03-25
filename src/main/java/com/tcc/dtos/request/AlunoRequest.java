package com.tcc.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record AlunoRequest(
        @NotBlank
        String nome) {
}
