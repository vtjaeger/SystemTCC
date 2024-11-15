package com.tcc.controller;

import com.tcc.dtos.request.banca.BancaRequest;
import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
//import com.tcc.models.Banca;
//import com.tcc.models.Professor;
//import com.tcc.repository.AlunoRepository;
//import com.tcc.repository.BancaRepository;
//import com.tcc.repository.ProfessorRepository;
import com.tcc.service.BancaService;
//import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/banca")
@CrossOrigin(origins = "*")
public class BancaController {
    @Autowired
    BancaService bancaService;

    @PostMapping
    public ResponseEntity registerBanca(@RequestBody @Valid BancaRequest bancaRequest) {
        return bancaService.registerBanca(bancaRequest);
    }

    @GetMapping
    public ResponseEntity<List<ApresentacaoBanca>> getAllBancas() {
        return bancaService.getAllBancas();
    }
}