package com.tcc.controller;

import com.tcc.dtos.request.professor.HorariosRequest;
import com.tcc.dtos.request.professor.ProfessorRequest;
import com.tcc.models.Coordenador;
import com.tcc.models.Professor;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/professor")
@Transactional
public class ProfessorController {
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    CoordenadorRepository coordenadorRepository;

    @PostMapping
    public ResponseEntity cadastrarProfessor(@RequestBody @Valid ProfessorRequest professorRequest){
        var novoProfessor = new Professor(professorRequest);

        return ResponseEntity.ok().body(professorRepository.save(novoProfessor));
    }

    @GetMapping
    public ResponseEntity getProfessores(){
        var professores = professorRepository.findAll();
        return ResponseEntity.ok().body(professores);
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneProfessor(@PathVariable(value = "id") Long id){
        var professor = professorRepository.findById(id);
        if(professor.isPresent()){
            return ResponseEntity.ok().body(professor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/horarios")
    public ResponseEntity horariosDisponiveis(@PathVariable(value = "id") Long id,
                                              @RequestBody @Valid HorariosRequest horariosRequest){

        List<Coordenador> coordenadores = coordenadorRepository.findAll()
                .stream().sorted(Comparator.comparing(Coordenador::getId))
                .collect(Collectors.toList());

        var coordenador = coordenadores.get(0);

        if(horariosRequest.getDateTime().isBefore(coordenador.getDataInicio()) || horariosRequest.getDateTime().isAfter(coordenador.getDataFinal())){
            return ResponseEntity.badRequest().body("horario indisponivel");
        }

        Optional<Professor> professorOptional = professorRepository.findById(id);
        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();

            LocalDateTime novoHorario = horariosRequest.getDateTime();

            if(professor.getHorariosDisponiveis().contains(novoHorario)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("horario ja cadastrado");
            }

            professor.adicionarDisponibilidade(novoHorario);

            professorRepository.save(professor);
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}