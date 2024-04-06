package com.tcc.controller;

import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
import com.tcc.service.ApresentacaoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
