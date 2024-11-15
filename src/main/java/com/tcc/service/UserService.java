package com.tcc.service;

import com.tcc.dtos.request.user.UserRequest;
import com.tcc.models.*;
import com.tcc.repository.AlunoRepository;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public ResponseEntity<User> saveUser(UserRequest userRequest) {
        User user = new User(userRequest.login(), userRequest.password());
        user.setEnabled(true);
        user.setRole(userRequest.role());
        user = userRepository.save(user);

        switch (userRequest.role()) {
            case ALUNO -> {
                Aluno aluno = new Aluno();
                aluno.setUser(user);
                aluno.setLogin(user.getLogin());
                alunoRepository.save(aluno);
                user.setAluno(aluno);
            }
            case PROFESSOR -> {
                Professor professor = new Professor();
                professor.setUser(user);
                professor.setLogin(user.getLogin());
                professorRepository.save(professor);
                user.setProfessor(professor);
            }
            case COORDENADOR -> {
                Coordenador coordenador = new Coordenador();
                coordenador.setUser(user);
                coordenador.setLogin(user.getLogin());
                coordenadorRepository.save(coordenador);
                user.setCoordenador(coordenador);
            }
            case ADMIN -> {
                user.setRole(UserRole.ADMIN);
            }
            default -> throw new IllegalArgumentException("invalid role");
        }
        userRepository.save(user);

        return ResponseEntity.ok().body(user);
    }

    public ResponseEntity getUsers(){
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok().body(userList);
    }
}