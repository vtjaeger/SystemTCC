package com.tcc.controller;

import com.tcc.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aluno")
@CrossOrigin(origins = "*")
public class AlunoController {
    @Autowired
    AlunoService alunoService;

    @GetMapping
    public ResponseEntity getAllAlunos(){
        return alunoService.getAllAlunos();
    }
}
