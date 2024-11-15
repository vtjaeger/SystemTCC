package com.tcc.dtos.response.coordenador;

import java.time.LocalDateTime;

public record CoordResponse(Long id, String login, String password, LocalDateTime dataInicio, LocalDateTime dataFinal) {
}
