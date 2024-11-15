package com.tcc.service;

import com.tcc.dtos.request.coordenador.DataInicio;
import com.tcc.dtos.response.coordenador.CoordResponse;
import com.tcc.models.Coordenador;
import com.tcc.repository.CoordenadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoordenadorService {
    @Autowired
    private CoordenadorRepository coordenadorRepository;

    public ResponseEntity getAllCoordenadores(){
        List<Coordenador> coordenadores = coordenadorRepository.findAll();
        List<CoordResponse> response = coordenadores.stream()
                .map(coord -> new CoordResponse(
                        coord.getId(),
                        coord.getLogin(),
                        coord.getUser().getPassword(),
                        coord.getDataInicio(),
                        coord.getDataFinal()))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }

    public ResponseEntity setDate(@PathVariable(value = "id") Long id, @RequestBody DataInicio dto){
        LocalDateTime dataInicio = dto.getDateTime();
        Optional<Coordenador> coordenadorOptional = coordenadorRepository.findById(id);

        if(coordenadorOptional.isPresent()) {
            var coordenador = coordenadorOptional.get();

            if(coordenador.getDataInicio() != null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("horario inicial ja cadastrado");
            }
            coordenador.setDataInicio(dataInicio);
            coordenador.setDataFinal(dataInicio.plusMonths(6));
            return ResponseEntity.ok().body(coordenadorRepository.save(coordenador));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("coordenador nao encontrado");
    }
}
