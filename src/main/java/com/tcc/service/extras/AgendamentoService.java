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

        //lista com objetos do tipo list<localdatetime> = cada professor tem uma lista de horarios
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
                horariosOrdenadosProfessores.get(1), horariosOrdenadosOrientador);


        if (!horariosEmComum.isEmpty()) {
            for (LocalDateTime horario : horariosEmComum) {

                if (!apresentacaoRepository.existsByDataHora(horario) ||
                        !apresentacaoRepository.existsByDataHora(horario.minusHours(1)) ||
                        !apresentacaoRepository.existsByDataHora(horario.plusHours(1)))

                    salvarApresentacao(banca.getId(), professores.get(0), professores.get(1), orientador,
                            horario);

                return horario;
            }
            return null;
        }

        List<Professor> todosProfessores = professorRepository.findAll();

        List<Coordenador> coordenadores = coordenadorRepository.findAll().stream()
                .sorted(Comparator.comparing(Coordenador::getId))
                .collect(Collectors.toList());

        var coordenador = coordenadores.get(0);

        LocalDateTime horaInicio = coordenador.getDataInicio();
        LocalDateTime horaFinal = coordenador.getDataFinal();

        List<Professor> professoresComHorariosIguais = new ArrayList<>();

        for (LocalDateTime horario = horaInicio; horario.isBefore(horaFinal) ||
                horario.equals(horaFinal); horario = horario.plusHours(1)) {

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