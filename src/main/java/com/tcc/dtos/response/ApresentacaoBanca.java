package com.tcc.dtos.response;

import com.tcc.models.Professor;

import java.time.LocalDateTime;
import java.util.List;

public record ApresentacaoBanca(Long bancaId, String titulo, String integrante1, String integran2, String integrante3,
                                Professor orientador, List<String> nomesProfessores, LocalDateTime dataHora) {
}
