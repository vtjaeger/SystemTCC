package com.tcc.controller;

import com.tcc.dtos.request.aluno.AlunoRequest;
import com.tcc.models.Aluno;
import com.tcc.repository.AlunoRepository;
import com.tcc.service.AlunoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aluno")
@Transactional
public class AlunoController {
    @Autowired
    AlunoRepository alunoRepository;
    @Autowired
    AlunoService alunoService;
    @PostMapping
    public ResponseEntity cadastrarAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        return alunoService.cadastrarAluno(alunoRequest);
    }

    @GetMapping
    public ResponseEntity getAllAlunos(){
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok().body(alunos);
    }

}
