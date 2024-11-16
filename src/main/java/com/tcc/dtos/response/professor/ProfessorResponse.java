package com.tcc.dtos.response.professor;

import java.time.LocalDateTime;
import java.util.List;

public record ProfessorResponse(Long id, String login, String senha, List<String> horarios) {
}
