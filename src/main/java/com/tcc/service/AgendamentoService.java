package com.tcc.service;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgendamentoService {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    BancaRepository bancaRepository;

    public LocalDateTime  marcarData(Banca banca){
        List<Professor> professores = banca.getProfessores();

        List<LocalDateTime> horariosEmComum = encontrarHorariosEmComumProfessoresDaBanca(professores.get(0), professores.get(1),
                professores.get(2));

        if (!horariosEmComum.isEmpty()) {
            LocalDateTime dataMarcada = horariosEmComum.get(0);

            salvarApresentacao(banca.getId(), professores.get(0), professores.get(1), professores.get(2), horariosEmComum.get(0));

            return dataMarcada;
        } else {
            List<Professor> todosProfessores = professorRepository.findAll();

            LocalDateTime horarioInicio = LocalDateTime.of(2024, 3, 22, 0, 0);
            LocalDateTime horarioFinal = LocalDateTime.of(2024, 3, 31, 0, 0);

            for(LocalDateTime horario = horarioInicio; horario.isBefore(horarioFinal); horario = horario.plusHours(1)){
                List<Professor> professoresComHorariosIguais = new ArrayList<>();

                for(Professor professor : todosProfessores){
                    if(professor.getHorariosDisponiveis().contains(horario)){
                        professoresComHorariosIguais.add(professor);
                    }

                    if(professoresComHorariosIguais.size() >= 3){
                        if(!apresentacaoRepository.existsByBancaId(banca.getId()) && !apresentacaoRepository.existsByDataHora(horario)){
                            salvarApresentacao(banca.getId(), professoresComHorariosIguais.get(0),
                                    professoresComHorariosIguais.get(1), professoresComHorariosIguais.get(2), horario);
                            return horario;
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<LocalDateTime> encontrarHorariosEmComumProfessoresDaBanca(Professor professor1, Professor professor2,
                                                                          Professor professor3){
        List<LocalDateTime> horarios1 = professor1.getHorariosDisponiveis();
        List<LocalDateTime> horarios2 = professor2.getHorariosDisponiveis();
        List<LocalDateTime> horarios3 = professor3.getHorariosDisponiveis();

        List<LocalDateTime> horariosEmComum = new ArrayList<>(horarios1);
        horariosEmComum.retainAll(horarios2);
        horariosEmComum.retainAll(horarios3);

        horariosEmComum.removeIf(horario -> apresentacaoRepository.existsByDataHora(horario));

        return horariosEmComum;
    }

    private void salvarApresentacao(Long bancaId, Professor professor1, Professor professor2, Professor professor3,
                                    LocalDateTime horario) {
        Apresentacao apresentacao = new Apresentacao(bancaId, professor1.getId(), professor2.getId(), professor3.getId(),
                horario);
        apresentacaoRepository.save(apresentacao);

        Banca banca = bancaRepository.findById(bancaId).orElseThrow();
        banca.setDataHoraApresentacao(horario);
        bancaRepository.save(banca);
    }
}