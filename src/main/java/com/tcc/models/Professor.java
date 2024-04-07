package com.tcc.models;

import com.tcc.dtos.request.professor.ProfessorRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_professores")
public class Professor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @ElementCollection
    private List<LocalDateTime> horariosDisponiveis;
    public Professor(ProfessorRequest professorRequest) {
        this.nome = professorRequest.nome();
    }

    public Professor(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Professor() {
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

    public List<LocalDateTime> getHorariosDisponiveis() {
        return horariosDisponiveis;
    }

    public void setHorariosDisponiveis(List<LocalDateTime> horariosDisponiveis) {
        this.horariosDisponiveis = horariosDisponiveis;
    }

    public void adicionarDisponibilidade(LocalDateTime datahora) {
        if(horariosDisponiveis == null){
            this.horariosDisponiveis = new ArrayList<>();
        }
        horariosDisponiveis.add(datahora);
    }
}