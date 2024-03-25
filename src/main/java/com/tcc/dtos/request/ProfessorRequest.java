package com.tcc.dtos.request;

import jakarta.validation.constraints.NotNull;

public record ProfessorRequest(
        @NotNull
        String nome) {
}
