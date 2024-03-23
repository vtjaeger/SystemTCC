package com.tcc.service;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.security.AllPermission;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;

    public void marcarDatas(Banca banca) {
        var professor1 = banca.getProfessores().get(0);
        var professor2 = banca.getProfessores().get(1);
        var professor3 = banca.getProfessores().get(2);

        List<LocalDateTime> horariosProfessor1 = professor1.getHorariosDisponiveis();
        List<LocalDateTime> horariosProfessor2 = professor2.getHorariosDisponiveis();
        List<LocalDateTime> horariosProfessor3 = professor3.getHorariosDisponiveis();

        List<LocalDateTime> horariosComuns = new ArrayList<>(horariosProfessor1);
        horariosComuns.retainAll(horariosProfessor2);
        horariosComuns.retainAll(horariosProfessor3);

        System.out.println(Arrays.toString(horariosComuns.toArray()));


        if(!horariosComuns.isEmpty()){
            for(int i = 0; i < horariosComuns.size(); i++){
                if(!apresentacaoRepository.existsByDataHora(horariosComuns.get(i))){
                    Apresentacao novaApresentacao = new Apresentacao(banca.getId(), professor1.getId(), professor2.getId(),
                            professor3.getId(), horariosComuns.get(i));

                    apresentacaoRepository.save(novaApresentacao);
                }
            }
        } else {
            List<Professor> todosProfessores = professorRepository.findAll();

        }
    }
}