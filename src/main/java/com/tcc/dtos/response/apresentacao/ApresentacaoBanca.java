package com.tcc.dtos.response.apresentacao;

import java.util.List;

public record ApresentacaoBanca(Long bancaId, String titulo, String integrante1, String integrante2, String integrante3,
                                String orientador, List<String> nomesProfessores, String dataHora) {
}
