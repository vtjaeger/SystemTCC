package com.tcc.service.extras;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Coordenador;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    BancaRepository bancaRepository;
    @Autowired
    CoordenadorRepository coordenadorRepository;

    public LocalDateTime marcarData(Banca banca) {
        List<Professor> professores = banca.getProfessores();
        List<LocalDateTime> horariosEmComum = encontrarHorariosEmComum(banca);

        if (!horariosEmComum.isEmpty()) {
            return agendarPrimeiroHorarioDisponivel(banca, professores, horariosEmComum);
        } else {
            return buscarHorarioAlternativo(banca, professores, banca.getOrientador());
        }
    }

    private LocalDateTime agendarPrimeiroHorarioDisponivel(Banca banca, List<Professor> professores, List<LocalDateTime> horariosEmComum) {
        for (LocalDateTime horario : horariosEmComum) {
            if (horarioDisponivel(horario)) {
                salvarApresentacao(banca.getId(), professores.get(0), professores.get(1), banca.getOrientador(), horario);
                return horario;
            }
        }
        return null;
    }

    private LocalDateTime buscarHorarioAlternativo(Banca banca, List<Professor> professores, Professor orientador) {
        LocalDateTime horaInicio = coordenadorRepository.findAll().stream()
                .sorted(Comparator.comparing(Coordenador::getId))
                .map(Coordenador::getDataInicio)
                .findFirst()
                .orElse(null);

        LocalDateTime horaFinal = horaInicio != null ? horaInicio.plusMonths(6) : null;

        if (horaInicio == null || horaFinal == null) return null;

        for (LocalDateTime horario = horaInicio; !horario.isAfter(horaFinal); horario = horario.plusHours(1)) {
            List<Professor> professoresDisponiveis = encontrarProfessoresDisponiveisNoHorario(horario, orientador);

            if (professoresDisponiveis.size() >= 3 && horarioDisponivel(horario)) {
                salvarApresentacao(banca.getId(), professoresDisponiveis.get(0), professoresDisponiveis.get(1), orientador, horario);
                return horario;
            }
        }
        return null;
    }

    private boolean horarioDisponivel(LocalDateTime horario) {
        return !apresentacaoRepository.existsByDataHora(horario.minusHours(1))
                && !apresentacaoRepository.existsByDataHora(horario)
                && !apresentacaoRepository.existsByDataHora(horario.plusHours(1));
    }

    private List<Professor> encontrarProfessoresDisponiveisNoHorario(LocalDateTime horario, Professor orientador) {
        return professorRepository.findAll().stream()
                .filter(professor -> professor.getHorariosDisponiveis().contains(horario)
                        && orientador.getHorariosDisponiveis().contains(horario))
                .collect(Collectors.toList());
    }

    private List<LocalDateTime> encontrarHorariosEmComum(Banca banca) {
        List<List<LocalDateTime>> horariosProfessores = banca.getProfessores().stream()
                .map(professor -> professor.getHorariosDisponiveis().stream()
                        .sorted()
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<LocalDateTime> horariosEmComum = new ArrayList<>(horariosProfessores.get(0));
        horariosEmComum.retainAll(horariosProfessores.get(1));
        horariosEmComum.retainAll(banca.getOrientador().getHorariosDisponiveis());

        return horariosEmComum.stream()
                .filter(this::horarioDisponivel)
                .collect(Collectors.toList());
    }

    private void salvarApresentacao(Long bancaId, Professor professor1, Professor professor2, Professor orientador, LocalDateTime horario) {
        if (!apresentacaoRepository.existsByBancaId(bancaId)) {
            Apresentacao apresentacao = new Apresentacao(bancaId, professor1.getId(), professor2.getId(), orientador.getId(), horario);
            apresentacaoRepository.save(apresentacao);

            Banca banca = bancaRepository.findById(bancaId).orElseThrow();
            banca.setDataHoraApresentacao(horario);
            bancaRepository.save(banca);
        }
    }
}