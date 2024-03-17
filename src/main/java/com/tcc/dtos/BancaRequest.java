package com.tcc.dtos;

import com.tcc.models.Professor;

import java.util.List;

public record BancaRequest(String titulo, String integrante1, String integrante2, String integrante3,
                           List<Professor> professores) {
}