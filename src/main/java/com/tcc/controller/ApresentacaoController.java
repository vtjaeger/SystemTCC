package com.tcc.controller;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.AgendamentoService;
import com.tcc.service.ProfessorService;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/apresentacao")
@Transactional
public class ApresentacaoController {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    BancaRepository bancaRepository;
    @Autowired
    AgendamentoService agendamentoService;

    @GetMapping
    public ResponseEntity getApresentacoes(){
        var bancas = apresentacaoRepository.findAll();
        return ResponseEntity.ok().body(bancas);
    }

    @GetMapping("/marcar")
    public ResponseEntity marcarHorario() {
        // adicionar data minima e maxima

        List<Banca> todasBancas = bancaRepository.findAll();

        for (Banca banca : todasBancas) {
            if (apresentacaoRepository.existsByBancaId(banca.getId())) {
                continue;
            }

            List<Professor> professores = banca.getProfessores();

            List<LocalDateTime> horariosComum = agendamentoService.encontrarHorariosComuns(professores);

            if (!horariosComum.isEmpty()) {
                Apresentacao novaApresentacao = new Apresentacao(banca.getId(), professores.get(0).getId(),
                        professores.get(1).getId(), professores.get(2).getId(), horariosComum.get(0));
                apresentacaoRepository.save(novaApresentacao);

                return ResponseEntity.ok().body(novaApresentacao);
            }
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body("Nenhum horário disponível para marcar apresentação.");
    }
}
