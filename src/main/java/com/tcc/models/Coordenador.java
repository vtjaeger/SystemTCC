package com.tcc.models;

import com.tcc.dtos.request.coordenador.CoordenadorRequest;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_coordenadores")
public class Coordenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;

    public Coordenador(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Coordenador() {
    }

    public Coordenador(CoordenadorRequest coordenadorRequest) {
        this.nome = coordenadorRequest.nome();
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

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDateTime dataFinal) {
        this.dataFinal = dataFinal;
    }
}
