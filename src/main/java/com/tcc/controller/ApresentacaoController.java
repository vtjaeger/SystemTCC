package com.tcc.controller;

import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
import com.tcc.models.Banca;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.AgendamentoService;
import com.tcc.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/apresentacao")
@Transactional
public class ApresentacaoController {
    @Autowired
    ApresentacaoRepository apresentacaoRepository;
    @Autowired
    ProfessorRepository professorRepository;
    @Autowired
    BancaRepository bancaRepository;
    @Autowired
    AgendamentoService agendamentoService;
    @Autowired
    EmailService emailService;

    @GetMapping
    public ResponseEntity<List<ApresentacaoBanca>> getApresentacoes() {
        List<ApresentacaoBanca> responseList = apresentacaoRepository.findAll().stream()
                .map(apresentacao -> new ApresentacaoBanca(
                        apresentacao.getBancaId(),
                        bancaRepository.findById(apresentacao.getBancaId()).get().getTitulo(),
                        bancaRepository.findById(apresentacao.getBancaId()).get().getIntegrante1(),
                        bancaRepository.findById(apresentacao.getBancaId()).get().getIntegrante2(),
                        bancaRepository.findById(apresentacao.getBancaId()).get().getIntegrante3(),
                        bancaRepository.findById(apresentacao.getBancaId()).get().getOrientador().getNome(),

                        List.of(
                                professorRepository.findById(apresentacao.getProfessor1Id()).get().getNome(),
                                professorRepository.findById(apresentacao.getProfessor2Id()).get().getNome()
                        ),
                        apresentacao.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/marcar")
    public ResponseEntity marcarDatas() {
        List<Banca> bancas = bancaRepository.findAll();

        for (Banca banca : bancas) {
            agendamentoService.marcarData(banca);
            String para = banca.getOrientador().getNome();
            String assunto = "Horario marcado para a banca: " + banca.getTitulo();
            String texto = "Alunos: \n" +
                    banca.getIntegrante1() + ", " + banca.getIntegrante2() + ", " + banca.getIntegrante3() + "\n\n" +
                    "Data e hora: " + banca.getDataHoraApresentacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));

            try {
                emailService.enviarEmail("viniciustjaeger@gmail.com", assunto, texto);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok().body(bancas);
    }
}
