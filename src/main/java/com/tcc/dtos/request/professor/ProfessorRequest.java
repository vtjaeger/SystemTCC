package com.tcc.dtos.request.professor;

import jakarta.validation.constraints.NotNull;

public record ProfessorRequest(
        @NotNull
        String nome) {
}
