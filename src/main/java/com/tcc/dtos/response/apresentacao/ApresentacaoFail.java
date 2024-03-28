package com.tcc.dtos.response.apresentacao;

import com.tcc.models.Professor;
import com.tcc.repository.ProfessorRepository;

import java.util.List;

public record ApresentacaoFail(Long id, String titulo, String integrante1, String integrante2, String integrante3,
                               Professor orientador, List<String> nomesProfessores, String mensagem) {
}
