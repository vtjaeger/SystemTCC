package com.tcc.controller;

import com.tcc.models.Apresentacao;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.ProfessorService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
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
    ProfessorService professorService;

    @GetMapping
    public ResponseEntity getBancas(){
        var bancas = apresentacaoRepository.findAll();
        return ResponseEntity.ok().body(bancas);
    }

//    @PostMapping
//    public ResponseEntity cadastrarApresentacao(@RequestBody ApresentacaoRequest apresentacaoRequest){
//        List<Professor> professores = professorRepository.findAll();
//        List<Professor> professorComHorarioIgual = new ArrayList<>();
//
//        for(Professor professor : professores){
//            boolean horariosIguais = true;
//
//            for(Professor outroProfessor : professores){
//                if(!professor.equals(outroProfessor) && professor.getHorariosDisponiveis().equals
//                        (outroProfessor.getHorariosDisponiveis())){
//                    horariosIguais = false;
//                    break;
//                }
//            }
//            if(horariosIguais){
//                professorComHorarioIgual.add(professor);
//            }
//        }
//        if(professorComHorarioIgual.size() >= 3){
//            LocalDateTime dataHoraApresentacao = apresentacaoRequest.dataHora();
//
//            Apresentacao novaApresentacao = new Apresentacao(apresentacaoRequest.bancaId(), professorComHorarioIgual.get(0),
//                    professorComHorarioIgual.get(1), professorComHorarioIgual.get(2), dataHoraApresentacao);
//            apresentacaoRepository.save(novaApresentacao);
//            return ResponseEntity.ok().body(novaApresentacao);
//        } else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro");
//        }
//    }

    @GetMapping("/marcar")
    public ResponseEntity marcarHorario(){
        LocalDateTime horarioInicial = LocalDateTime.of(2024, 3, 10, 9,0);
        LocalDateTime horarioFinal = LocalDateTime.of(2024, 4, 30, 23,0);

        List<Banca> todasBancas = bancaRepository.findAll();
        List<Professor> todosProfessores = professorRepository.findAll();

        for(Banca banca : todasBancas){
            List<Professor> professores = banca.getProfessores();

            List<LocalDateTime> horariosComum = professorService.encontrarHorariosComuns(professores);
            if(!horariosComum.isEmpty()){
                LocalDateTime dataHoraApresentacao = horariosComum.get(0);
                if(apresentacaoRepository.existsByDataHora(dataHoraApresentacao)){
                    continue;
                }

                Apresentacao novaApresentacao = new Apresentacao(banca.getId(), professores.get(0).getId(),
                        professores.get(1).getId(), professores.get(2).getId(), dataHoraApresentacao);
                apresentacaoRepository.save(novaApresentacao);
            }
        }
        return ResponseEntity.ok().body("ok");
    }
}
