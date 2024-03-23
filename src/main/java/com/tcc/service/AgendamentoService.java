package com.tcc.service;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

        List<LocalDateTime> horariosP1 = professor1.getHorariosDisponiveis();
        List<LocalDateTime> horariosP2 = professor2.getHorariosDisponiveis();
        List<LocalDateTime> horariosP3 = professor3.getHorariosDisponiveis();

        List<LocalDateTime> horariosComum = new ArrayList<>(horariosP1);
        horariosComum.retainAll(horariosP2);
        horariosComum.retainAll(horariosP3);

        if (!horariosComum.isEmpty()) {
            for (LocalDateTime horario : horariosComum) {
                if (!apresentacaoRepository.existsByDataHora(horario)) {
                    salvarApresentacao(banca.getId(), professor1, professor2, professor3, horario);
                    professor1.getHorariosDisponiveis().remove(horario);
                    professor2.getHorariosDisponiveis().remove(horario);
                    professor3.getHorariosDisponiveis().remove(horario);
                } else {
                    System.out.println(horario + " ja cadastrado");
                }

            }
        }
    }

    public void salvarApresentacao(Long bancaId, Professor professor1, Professor professor2, Professor professor3, LocalDateTime horario) {
        var novaApresentacao = new Apresentacao(bancaId, professor1.getId(), professor2.getId(), professor3.getId(), horario);
        apresentacaoRepository.save(novaApresentacao);
    }
}