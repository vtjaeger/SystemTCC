package com.tcc.controller;

import com.tcc.dtos.BancaRequest;
import com.tcc.dtos.response.BancaResponse;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.AlunoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/banca")
@Transactional
public class BancaController {
    @Autowired
    BancaRepository bancaRepository;
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    ProfessorRepository professorRepository;

    @PostMapping
    public ResponseEntity cadastrarBanca(@RequestBody @Valid BancaRequest bancaRequest) {
        if (!alunoRepository.existsByNome(bancaRequest.integrante1()) ||
                !alunoRepository.existsByNome(bancaRequest.integrante2()) ||
                !alunoRepository.existsByNome(bancaRequest.integrante3())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("integrante nao encontrado");
        }

        List<Professor> professores = professorRepository.findAll();
        for (Professor professor : bancaRequest.professores()) {
            Professor foundProfessor = professorRepository.findByNome(professor.getNome());
            if (foundProfessor == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Professor não encontrado: " + professor.getNome());
            }
            professores.add(foundProfessor);
        }

        Banca novaBanca = new Banca(bancaRequest.titulo(), bancaRequest.integrante1(),
                bancaRequest.integrante2(), bancaRequest.integrante3(), professores);

        Banca savedBanca = bancaRepository.save(novaBanca);

        return ResponseEntity.ok().body(savedBanca);
    }


    @GetMapping
    public ResponseEntity<List<BancaResponse>> getAllBancas() {
        List<Banca> bancas = bancaRepository.findAll();
        List<BancaResponse> responseList = bancas.stream()
                .map(banca -> new BancaResponse(
                        banca.getId(),
                        banca.getTitulo(),
                        banca.getIntegrante1(),
                        banca.getIntegrante2(),
                        banca.getIntegrante3(),
                        banca.getOrientador(),
                        banca.getProfessores()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }
}
