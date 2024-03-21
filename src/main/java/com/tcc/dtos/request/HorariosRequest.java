package com.tcc.dtos.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record HorariosRequest(String data, String horaInicio) {
    public LocalDateTime getDateTime(){
        LocalDate data = LocalDate.parse(data());
        LocalTime hora = LocalTime.parse(horaInicio());
        return LocalDateTime.of(data, hora);
    }
}
