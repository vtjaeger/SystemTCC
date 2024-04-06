package com.tcc.service;

import com.tcc.dtos.response.apresentacao.ApresentacaoBanca;
import com.tcc.models.Banca;
import com.tcc.models.Professor;
import com.tcc.repository.ApresentacaoRepository;
import com.tcc.repository.BancaRepository;
import com.tcc.repository.ProfessorRepository;
import com.tcc.service.extras.AgendamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApresentacaoService {
    @Autowired
    private ApresentacaoRepository apresentacaoRepository;
    @Autowired
    private BancaRepository bancaRepository;
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    AgendamentoService agendamentoService;

    public ResponseEntity<List<ApresentacaoBanca>> getAllApresentacoes(){
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

    public ResponseEntity marcarDatas(){
        List<Banca> bancas = bancaRepository.findAll();
        List<ApresentacaoBanca> response = new ArrayList<>();

        for (Banca banca : bancas) {
            agendamentoService.marcarData(banca);
//            String para = banca.getOrientador().getNome();
//            String assunto = "Horario marcado para a banca: " + banca.getTitulo();
//            String texto = "Alunos: \n" +
//                    banca.getIntegrante1() + ", " + banca.getIntegrante2() + ", " + banca.getIntegrante3() + "\n\n" +
//                    "Data e hora: " + banca.getDataHoraApresentacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
//
//            try {
//                emailService.enviarEmail("viniciustjaeger@gmail.com", assunto, texto);
//            } catch (MessagingException e) {
//                throw new RuntimeException(e);
//            }

            var apresentacao = new ApresentacaoBanca(
                    banca.getId(),
                    banca.getTitulo(),
                    banca.getIntegrante1(),
                    banca.getIntegrante2(),
                    banca.getIntegrante3(),
                    banca.getOrientador().getNome(),
                    banca.getProfessores().stream().map(Professor::getNome).collect(Collectors.toList()),
                    banca.getDataHoraApresentacao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            );
            response.add(apresentacao);
        }

        return ResponseEntity.ok().body(response);
    }
}
