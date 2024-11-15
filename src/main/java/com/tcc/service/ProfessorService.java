package com.tcc.service;

import com.tcc.dtos.request.professor.HorariosRequest;
import com.tcc.dtos.response.professor.ProfessorResponse;
import com.tcc.models.Coordenador;
import com.tcc.models.Professor;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public ResponseEntity getAllProfessores(){
        List<Professor> professores = professorRepository.findAll();
        List<ProfessorResponse> response = professores.stream()
                .map(p -> new ProfessorResponse(
                        p.getId(),
                        p.getLogin(),
                        p.getUser().getPassword()
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity getOneProfessor(@PathVariable(value = "id") Long id){
        var professor = professorRepository.findById(id);
        if(professor.isPresent()){
            return ResponseEntity.ok().body(professor);
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity setAvailableTimes(Long id, HorariosRequest horariosRequest){

        List<Coordenador> coordenadores = coordenadorRepository.findAll()
                .stream().sorted(Comparator.comparing(Coordenador::getId))
                .collect(Collectors.toList());

        if(coordenadores.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("informar coordenador");
        }

        var coordenador = coordenadores.get(0);

        if(horariosRequest.getDateTime().isBefore(coordenador.getDataInicio()) || horariosRequest.getDateTime().isAfter(coordenador.getDataFinal())){
            return ResponseEntity.badRequest().body("horario indisponivel");
        }

        Optional<Professor> professorOptional = professorRepository.findById(id);
        if(professorOptional.isPresent()){
            Professor professor = professorOptional.get();

            LocalDateTime novoHorario = horariosRequest.getDateTime();

            if(professor.getHorariosDisponiveis().contains(novoHorario)){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("horario ja cadastrado");
            }

            professor.adicionarDisponibilidade(novoHorario);

            professorRepository.save(professor);
            return ResponseEntity.ok().body("ok");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
