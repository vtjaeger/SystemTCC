package com.tcc.dtos.request.apresentacao;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ApresentacaoRequest(
        @NotNull
        Long bancaId,
        @NotNull
        Long professor1Id,
        @NotNull
        Long professor2Id,
        @NotNull
        Long professor3Id,
        @NotNull
        LocalDateTime dataHora) {
}
