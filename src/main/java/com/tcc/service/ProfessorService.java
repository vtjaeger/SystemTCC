package com.tcc.service;

import com.tcc.models.Professor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfessorService {
    public List<LocalDateTime> encontrarHorariosComuns(List<Professor> professores) {
        List<LocalDateTime> horariosComuns = new ArrayList<>();

        if (!professores.isEmpty()) {
            horariosComuns.addAll(professores.get(0).getHorariosDisponiveis());

            for (int i = 1; i < professores.size(); i++) {
                List<LocalDateTime> horariosProfessorAtual = professores.get(i).getHorariosDisponiveis();
                horariosComuns.retainAll(horariosProfessorAtual);
            }
            return horariosComuns;
        } else {
            return null;
            //nenhum horario comum
        }
    }
}
