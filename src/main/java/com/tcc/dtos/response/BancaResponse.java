package com.tcc.dtos.response;

import com.tcc.models.Banca;
import com.tcc.models.Professor;

import java.util.List;

public record BancaResponse(Long id, String titulo, String integrante1, String integrante2, String integrante3, String orientador,
                            List<String> nomesProfessores) {
}
