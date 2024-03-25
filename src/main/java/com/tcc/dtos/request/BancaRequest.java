package com.tcc.dtos.request;

import com.tcc.models.Professor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BancaRequest(
        @NotBlank
        String titulo,
        @NotBlank
        String integrante1,
        @NotBlank
        String integrante2,
        @NotBlank
        String integrante3,
        @NotBlank
        @Size(min = 2, max = 4)
        List<Professor> professores) {
}