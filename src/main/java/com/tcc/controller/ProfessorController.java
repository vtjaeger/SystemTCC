package com.tcc.controller;

import com.tcc.dtos.HorariosRequest;
import com.tcc.dtos.ProfessorRequest;
import com.tcc.models.Professor;
import com.tcc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/professor")
@Transactional
public class ProfessorController {
    @Autowired
    ProfessorRepository professorRepository;
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
        Optional<Professor> professorOptional = professorRepository.findById(id);
        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();
            professor.adicionarDisponibilidade(horariosRequest.data(), horariosRequest.horaInicio());

            professorRepository.save(professor);
            return ResponseEntity.ok().body("ok");
        } else{
            return ResponseEntity.notFound().build();
        }
    }
}
