package com.tcc.controller;

import com.tcc.dtos.request.aluno.AlunoRequest;
import com.tcc.models.Aluno;
import com.tcc.repository.AlunoRepository;
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
    @PostMapping
    public ResponseEntity cadastrarAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        var novoAluno = new Aluno(alunoRequest);

        if(alunoRepository.existsByNome(alunoRequest.nome())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(alunoRequest.nome() + " ja registrado");
        }

        return ResponseEntity.ok().body(alunoRepository.save(novoAluno));
    }

    @GetMapping
    public ResponseEntity getAllAlunos(){
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok().body(alunos);
    }

}
