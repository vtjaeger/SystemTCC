package com.tcc.service;

import com.tcc.dtos.request.banca.BancaRequest;
import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.AlunoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BancaService {
    @Autowired
    private BancaRepository bancaRepository;
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    public ResponseEntity registerBanca(@RequestBody @Valid BancaRequest bancaRequest){
        if (!alunoRepository.existsByNome(bancaRequest.integrante1().trim()) ||
                !alunoRepository.existsByNome(bancaRequest.integrante2().trim()) ||
                !alunoRepository.existsByNome(bancaRequest.integrante3().trim())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("integrante nao encontrado");
        }

        if (bancaRepository.existsByIntegrante1OrIntegrante2OrIntegrante3(bancaRequest.integrante1(), bancaRequest.integrante2(),
                bancaRequest.integrante3())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("integrante vinculado a outra banca");
        }

        Professor orientador = professorRepository.findByNome(bancaRequest.orientador());

        if(orientador == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erro");
        }

        List<Professor> professores = new ArrayList<>();

        for (Professor professorRequest : bancaRequest.professores()) {
            Professor professorAtual = professorRepository.findByNome(professorRequest.getNome());

            if (professorAtual == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("professor nao encontrado");
            }
            professores.add(professorAtual);
        }

        Banca novaBanca = new Banca(bancaRequest.titulo(), bancaRequest.integrante1(),
                bancaRequest.integrante2(), bancaRequest.integrante3(), orientador, professores);

        Banca savedBanca = bancaRepository.save(novaBanca);

        return ResponseEntity.ok().body(savedBanca);
    }

    public ResponseEntity<List<ApresentacaoBanca>> getAllBancas(){
        List<Banca> bancas = bancaRepository.findAll();
        List<ApresentacaoBanca> responseList = bancas.stream()
                .map(banca -> {

                    if (banca.getDataHoraApresentacao() == null) {
                        return new ApresentacaoBanca(banca.getId(), banca.getTitulo(), banca.getIntegrante1(),
                                banca.getIntegrante2(), banca.getIntegrante3(), banca.getOrientador().getNome(),
                                null, null);

                    } else {
                        List<String> nomesProfessores = banca.getProfessores().stream()
                                .map(Professor::getNome)
                                .collect(Collectors.toList());

                        return new ApresentacaoBanca(
                                banca.getId(),
                                banca.getTitulo(),
                                banca.getIntegrante1(),
                                banca.getIntegrante2(),
                                banca.getIntegrante3(),
                                banca.getOrientador().getNome(),
                                nomesProfessores,
                                banca.getDataHoraApresentacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                        );

                    }
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseList);
    }
}
