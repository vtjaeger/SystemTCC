package com.tcc.controller;

import com.tcc.dtos.request.professor.HorariosRequest;
import com.tcc.dtos.request.professor.ProfessorRequest;
import com.tcc.models.Coordenador;
import com.tcc.models.Professor;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.ProfessorService;
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
    ProfessorService professorService;

    @PostMapping
    public ResponseEntity registerProfessor(@RequestBody @Valid ProfessorRequest professorRequest){
        return professorService.registerProfessor(professorRequest);
    }

    @GetMapping
    public ResponseEntity getProfessores(){
        return professorService.getAllProfessores();
    }

    @GetMapping("/{id}")
    public ResponseEntity getOneProfessor(@PathVariable(value = "id") Long id){
        return professorService.getOneProfessor(id);
    }

    @PostMapping("/{id}/horarios")
    public ResponseEntity setAvailableTimes(@PathVariable(value = "id") Long id,
                                              @RequestBody @Valid HorariosRequest horariosRequest) {
        return professorService.setAvailableTimes(id, horariosRequest);
    }
}