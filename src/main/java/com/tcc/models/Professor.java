package com.tcc.models;

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
    private String login;
    @ElementCollection
    private List<LocalDateTime> horariosDisponiveis;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Professor(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Professor() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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