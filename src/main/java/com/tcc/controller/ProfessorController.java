package com.tcc.controller;

import com.tcc.dtos.request.professor.HorariosRequest;
//import com.tcc.models.Coordenador;
//import com.tcc.models.Professor;
//import com.tcc.repository.CoordenadorRepository;
//import com.tcc.repository.ProfessorRepository;
import com.tcc.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/professor")
@CrossOrigin(origins = "*")
public class ProfessorController {
    @Autowired
    ProfessorService professorService;

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