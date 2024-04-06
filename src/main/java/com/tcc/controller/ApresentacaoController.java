package com.tcc.controller;

import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.AgendamentoService;
import com.tcc.service.ApresentacaoService;
import com.tcc.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apresentacao")
@Transactional
public class ApresentacaoController {
    @Autowired
    ApresentacaoService apresentacaoService;

    @GetMapping
    public ResponseEntity<List<ApresentacaoBanca>> getApresentacoes() {
        return apresentacaoService.getAllApresentacoes();
    }

    @GetMapping("/marcar")
    public ResponseEntity marcarDatas() {
        return apresentacaoService.marcarDatas();
    }
}
