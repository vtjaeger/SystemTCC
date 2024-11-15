package com.tcc.service;

import com.tcc.dtos.response.aluno.AlunoResponse;
import com.tcc.models.Aluno;
import com.tcc.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;

    public ResponseEntity getAllAlunos(){
        List<Aluno> alunos = alunoRepository.findAll();
        List<AlunoResponse> response = alunos.stream()
                .map(aluno -> new AlunoResponse(
                        aluno.getLogin(),
                        aluno.getLogin()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}
