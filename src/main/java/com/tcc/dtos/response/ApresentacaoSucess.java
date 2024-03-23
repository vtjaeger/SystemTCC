package com.tcc.dtos.response;

import java.time.LocalDateTime;
import java.util.List;

public record ApresentacaoSucess(Long id, String titulo, String integrante1, String integrante2, String integrante3, String orientador,
                                 List<String> nomesProfessores) {
}
