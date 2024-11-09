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

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public ResponseEntity<Aluno> registerAluno(@RequestBody @Valid AlunoRequest alunoRequest){
        var aluno = new Aluno(alunoRequest);
        if(!alunoRepository.existsByNome(aluno.getNome())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok().body(alunoRepository.save(aluno));
    }

    public ResponseEntity getAllAlunos(){
        List<Aluno> alunos = alunoRepository.findAll();
        return ResponseEntity.ok().body(alunos);
    }
}
