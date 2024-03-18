package com.tcc.controller;

import com.tcc.dtos.request.AlunoRequest;
import com.tcc.models.Aluno;
import com.tcc.repository.AlunoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
@Transactional
public class AlunoController {
    @Autowired
    AlunoRepository alunoRepository;
    @PostMapping
    public ResponseEntity cadastrarAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        var novoAluno = new Aluno(alunoRequest);

        return ResponseEntity.ok().body(alunoRepository.save(novoAluno));
    }

}
