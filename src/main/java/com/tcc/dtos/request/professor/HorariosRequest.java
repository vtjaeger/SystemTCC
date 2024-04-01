package com.tcc.dtos.request.professor;

import jakarta.validation.constraints.NotBlank;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record HorariosRequest(
        @NotBlank
        String data,
        @NotBlank
        String horaInicio) {
    public LocalDateTime getDateTime(){
        LocalDate localDate = LocalDate.parse(data, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalTime localTime = LocalTime.parse(horaInicio, DateTimeFormatter.ofPattern("HH:mm"));
        return LocalDateTime.of(localDate, localTime);
    }
}
