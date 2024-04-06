package com.tcc.controller;

import com.tcc.dtos.request.aluno.AlunoRequest;
import com.tcc.service.AlunoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
@Transactional
public class AlunoController {
    @Autowired
    AlunoService alunoService;
    @PostMapping
    public ResponseEntity registerAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        return alunoService.registerAluno(alunoRequest);
    }

    @GetMapping
    public ResponseEntity getAllAlunos(){
        return alunoService.getAllAlunos();
    }
}
