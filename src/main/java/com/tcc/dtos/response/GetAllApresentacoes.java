package com.tcc.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record GetAllApresentacoes(String titulo, String integrante1, String integran2, String integrante3,
                                  List<String> nomesProfessores, LocalDateTime dataHora) {
}
