package com.tcc.service;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;

    public void marcarDatas(Banca banca){
        List<Professor> professores = banca.getProfessores();
        List<LocalDateTime> horariosComum = encontrarHorariosComuns(professores);

        for(LocalDateTime horarioComum : horariosComum){
            if(!apresentacaoRepository.existsByBancaId(banca.getId())){
                if(!apresentacaoRepository.existsByDataHora(horarioComum)){
                    Apresentacao novaApresentacao = new Apresentacao(banca.getId(), professores.get(0).getId(),
                            professores.get(1).getId(), professores.get(2).getId(), horarioComum);

                    apresentacaoRepository.save(novaApresentacao);
                }
            }
        }

    }
    public List<LocalDateTime> encontrarHorariosComuns(List<Professor> professores) {

        if (!professores.isEmpty()) {
            List<LocalDateTime> horariosComuns = new ArrayList<>(professores.get(0).getHorariosDisponiveis());

            for (int i = 1; i < professores.size(); i++) {
                List<LocalDateTime> horariosProfessorAtual = professores.get(i).getHorariosDisponiveis();
                horariosComuns.retainAll(horariosProfessorAtual);
            }
            return horariosComuns;
        } else {
            return null;
        }
    }
}
