package com.tcc.dtos;

import java.time.LocalDateTime;

public record ApresentacaoRequest(Long bancaId, Long professor1Id, Long professor2Id, Long professor3Id, LocalDateTime dataHora) {
}
