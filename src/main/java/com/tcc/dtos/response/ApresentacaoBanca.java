package com.tcc.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record ApresentacaoBanca(Long bancaId, String titulo, String integrante1, String integran2, String integrante3,
                                String orientador,  List<String> nomesProfessores, LocalDateTime dataHora) {
}
