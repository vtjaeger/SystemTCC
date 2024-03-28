package com.tcc.dtos.request.banca;

import com.tcc.models.Professor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
        String orientador,
        @NotNull
        @NotEmpty
        List<Professor> professores) {
}