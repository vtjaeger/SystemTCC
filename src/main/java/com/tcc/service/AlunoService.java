package com.tcc.service;

import com.tcc.dtos.request.aluno.AlunoRequest;
import com.tcc.models.Aluno;
import com.tcc.repository.AlunoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AlunoService {
    @Autowired
    AlunoRepository alunoRepository;

    public ResponseEntity<Aluno> cadastrarAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        var aluno = new Aluno(alunoRequest);

        if(!alunoRepository.existsByNome(aluno.getNome())){

            return ResponseEntity.ok().body(alunoRepository.save(aluno));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
