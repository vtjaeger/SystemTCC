package com.tcc.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_apresentacao")
public class Apresentacao {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long bancaId;
    private Long professor1Id;
    private Long professor2Id;
    private Long professor3Id;
    private Long orientadorId;
    private LocalDateTime dataHora;

    public Apresentacao() {
    }

    public Apresentacao(Long bancaId, Long professor1Id, Long professor2Id, Long orientadorId, LocalDateTime dataHora) {
        this.bancaId = bancaId;
        this.professor1Id = professor1Id;
        this.professor2Id = professor2Id;
        this.orientadorId = orientadorId;
        this.dataHora = dataHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBancaId() {
        return bancaId;
    }

    public void setBancaId(Long bancaId) {
        this.bancaId = bancaId;
    }

    public Long getProfessor1Id() {
        return professor1Id;
    }

    public void setProfessor1Id(Long professor1Id) {
        this.professor1Id = professor1Id;
    }

    public Long getProfessor2Id() {
        return professor2Id;
    }

    public void setProfessor2Id(Long professor2Id) {
        this.professor2Id = professor2Id;
    }

    public Long getProfessor3Id() {
        return professor3Id;
    }

    public void setProfessor3Id(Long professor3Id) {
        this.professor3Id = professor3Id;
    }

    public Long getOrientadorId() {
        return orientadorId;
    }

    public void setOrientadorId(Long orientadorId) {
        this.orientadorId = orientadorId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
