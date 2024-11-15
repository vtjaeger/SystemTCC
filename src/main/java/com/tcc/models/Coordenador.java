package com.tcc.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_coordenadores")
public class Coordenador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Coordenador(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Coordenador() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
