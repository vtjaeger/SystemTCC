package com.tcc.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tb_banca")
public class Banca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String integrante1;
    private String integrante2;
    private String integrante3;
    private LocalDateTime dataHoraApresentacao;
    @ManyToOne
    private Professor orientador;
    @ManyToMany
    private List<Professor> professores;

    public Banca(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Banca() {
    }

    public Banca(String titulo, String integrante1, String integrante2, String integrante3, LocalDateTime dataHoraApresentacao,
                 Professor orientador, List<Professor> professores) {
        this.titulo = titulo;
        this.integrante1 = integrante1;
        this.integrante2 = integrante2;
        this.integrante3 = integrante3;
        this.dataHoraApresentacao = dataHoraApresentacao;
        this.orientador = orientador;
        this.professores = professores;
    }

    public Banca(String titulo, String integrante1, String integrante2, String integrante3, Professor orientador,
                 List<Professor> professores) {
        this.titulo = titulo;
        this.integrante1 = integrante1;
        this.integrante2 = integrante2;
        this.integrante3 = integrante3;
        this.orientador = orientador;
        this.professores = professores;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIntegrante1() {
        return integrante1;
    }

    public void setIntegrante1(String integrante1) {
        this.integrante1 = integrante1;
    }

    public String getIntegrante2() {
        return integrante2;
    }

    public void setIntegrante2(String integrante2) {
        this.integrante2 = integrante2;
    }

    public String getIntegrante3() {
        return integrante3;
    }

    public void setIntegrante3(String integrante3) {
        this.integrante3 = integrante3;
    }

    public LocalDateTime getDataHoraApresentacao() {
        return dataHoraApresentacao;
    }

    public void setDataHoraApresentacao(LocalDateTime dataHoraApresentacao) {
        this.dataHoraApresentacao = dataHoraApresentacao;
    }

    public Professor getOrientador() {
        return orientador;
    }

    public void setOrientador(Professor orientador) {
        this.orientador = orientador;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
}
