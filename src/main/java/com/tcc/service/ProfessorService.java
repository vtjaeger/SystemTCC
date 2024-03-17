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

        // Verifica se a lista de professores não está vazia antes de prosseguir
        if (!professores.isEmpty()) {
            // Inicialmente, assumimos que os horários disponíveis do primeiro professor são comuns a todos
            horariosComuns.addAll(professores.get(0).getHorariosDisponiveis());

            // Para cada professor, restringimos os horários comuns aos horários que também estão disponíveis para o professor atual
            for (int i = 1; i < professores.size(); i++) {
                List<LocalDateTime> horariosProfessorAtual = professores.get(i).getHorariosDisponiveis();
                horariosComuns.retainAll(horariosProfessorAtual);
            }
        } else {
            // Lidar com o caso em que a lista de professores está vazia
            System.out.println("A lista de professores está vazia.");
        }

        return horariosComuns;
    }
}
