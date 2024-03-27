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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    BancaRepository bancaRepository;

    public LocalDateTime marcarData(Banca banca) {
        List<Professor> professores = banca.getProfessores();

        List<List<LocalDateTime>> horariosOrdenadosProfessores = new ArrayList<>();

        for (Professor professorBanca : professores) {
            List<LocalDateTime> horariosOrdenados = professorBanca.getHorariosDisponiveis()
                    .stream()
                    .sorted(Comparator.naturalOrder())
                    .collect(Collectors.toList());
            horariosOrdenadosProfessores.add(horariosOrdenados);
        }

        Professor orientador = banca.getOrientador();

        List<LocalDateTime> horariosOrdenadosOrientador = banca.getOrientador().getHorariosDisponiveis()
                .stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());


        List<LocalDateTime> horariosEmComum = encontrarHorariosEmComumProfessoresDaBanca(horariosOrdenadosProfessores.get(0),
                horariosOrdenadosProfessores.get(0), horariosOrdenadosOrientador);

        if (!horariosEmComum.isEmpty()) {
            LocalDateTime dataMarcada = horariosEmComum.get(0);

            if (apresentacaoRepository.existsByDataHora(horariosEmComum.get(0).minusHours(1)) ||
                    apresentacaoRepository.existsByDataHora(horariosEmComum.get(0).plusHours(1))) {
                return null;
            } else {
                salvarApresentacao(banca.getId(), professores.get(0), professores.get(1), orientador,
                        horariosEmComum.get(0));
                return dataMarcada;
            }
        }
        List<Professor> todosProfessores = professorRepository.findAll();

        LocalDateTime horarioInicio = LocalDateTime.of(2024, 3, 22, 0, 0);
        LocalDateTime horarioFinal = LocalDateTime.of(2024, 3, 31, 0, 0);

        for (LocalDateTime horario = horarioInicio; horario.isBefore(horarioFinal); horario = horario.plusHours(1)) {
            List<Professor> professoresComHorariosIguais = new ArrayList<>();

            for (Professor professor : todosProfessores) {

                if (orientador.getHorariosDisponiveis().contains(horario) && professor.getHorariosDisponiveis().contains(horario)
                        && !apresentacaoRepository.existsByDataHora(horario.minusHours(1)) &&
                        !apresentacaoRepository.existsByDataHora(horario.plusHours(1)) &&
                        !apresentacaoRepository.existsByDataHora(horario)) {

                    professoresComHorariosIguais.add(professor);
                }

                if (professoresComHorariosIguais.size() >= 3 && !apresentacaoRepository.existsByBancaId(banca.getId())) {
                    salvarApresentacao(banca.getId(), professoresComHorariosIguais.get(0),
                            professoresComHorariosIguais.get(1), orientador, horario);
                    return horario;
                }
            }
        }
        return null;
    }

    public List<LocalDateTime> encontrarHorariosEmComumProfessoresDaBanca(List<LocalDateTime> horariosP1,
                                                                          List<LocalDateTime> horariosP2,
                                                                          List<LocalDateTime> horariosOrientador) {
        List<LocalDateTime> horarios1 = horariosP1;
        List<LocalDateTime> horarios2 = horariosP2;
        List<LocalDateTime> horarios3 = horariosOrientador;

        List<LocalDateTime> horariosEmComum = new ArrayList<>(horarios1);
        horariosEmComum.retainAll(horarios2);
        horariosEmComum.retainAll(horarios3);

        horariosEmComum.removeIf(horario -> apresentacaoRepository.existsByDataHora(horario.minusHours(1)));
        horariosEmComum.removeIf(horario -> apresentacaoRepository.existsByDataHora(horario.plusHours(1)));
        horariosEmComum.removeIf(horario -> apresentacaoRepository.existsByDataHora(horario));
        return horariosEmComum;
    }

    private void salvarApresentacao(Long bancaId, Professor professor1, Professor professor2,
                                    Professor orientador, LocalDateTime horario) {
        Apresentacao apresentacao = new Apresentacao(bancaId, professor1.getId(), professor2.getId(),
                orientador.getId(), horario);
        apresentacaoRepository.save(apresentacao);

        Banca banca = bancaRepository.findById(bancaId).orElseThrow();
        banca.setDataHoraApresentacao(horario);
        bancaRepository.save(banca);
    }
}