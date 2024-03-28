package com.tcc.dtos.request.coordenador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DataInicio(String dataInicio, String horaInicio) {
    public LocalDateTime getDateTime(){
        LocalDate data = LocalDate.parse(dataInicio);
        LocalTime hora = LocalTime.parse(horaInicio);
        return LocalDateTime.of(data, hora);
    }
}
