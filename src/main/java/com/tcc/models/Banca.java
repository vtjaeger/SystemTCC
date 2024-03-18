package com.tcc.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tb_banca")
public class Banca {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String titulo;
    private String integrante1;
    private String integrante2;
    private String integrante3;

    private final String orientador = "Matheus";

    @ManyToMany
    private List<Professor> professores;

    public Banca(Long id, String titulo) {
        this.id = id;
        this.titulo = titulo;
    }

    public Banca() {
    }

    public Banca(String titulo, String integrante1, String integrante2, String integrante3, List<Professor> professores) {
        this.titulo = titulo;
        this.integrante1 = integrante1;
        this.integrante2 = integrante2;
        this.integrante3 = integrante3;
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

    public String getOrientador() {
        return orientador;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }
}
