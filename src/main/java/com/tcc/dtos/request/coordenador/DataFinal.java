package com.tcc.dtos.request.coordenador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record DataFinal(String dataFinal, String horaFinal) {
    public LocalDateTime getDateTime(){
        LocalDate data = LocalDate.parse(dataFinal);
        LocalTime hora = LocalTime.parse(horaFinal);
        return LocalDateTime.of(data, hora);
    }
}
