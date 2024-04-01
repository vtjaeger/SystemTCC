package com.tcc.dtos.response.apresentacao;

import com.tcc.models.Professor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public record ApresentacaoBanca(Long bancaId, String titulo, String integrante1, String integrante2, String integrante3,
                                String orientador, List<String> nomesProfessores, String dataHora) {
}
