package com.tcc.models;

import com.tcc.dtos.request.AlunoRequest;
import jakarta.persistence.*;

@Entity
@Table(name = "tb_alunos")
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String nome;

    public Aluno(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Aluno() {
    }

    public Aluno(AlunoRequest alunoRequest) {
        this.nome = alunoRequest.nome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
