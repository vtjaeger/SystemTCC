package com.tcc.controller;

import com.tcc.dtos.request.coordenador.DataFinal;
import com.tcc.dtos.request.coordenador.CoordenadorRequest;
import com.tcc.dtos.request.coordenador.DataInicio;
import com.tcc.models.Coordenador;
import com.tcc.repository.CoordenadorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/coordenadores")
public class CoordenadorController {
    @Autowired
    CoordenadorRepository coordenadorRepository;

    @PostMapping
    public ResponseEntity cadastrarCoordenador(@RequestBody @Valid CoordenadorRequest coordenadorRequest){
        var novoCoordenador = new Coordenador(coordenadorRequest);
        var savedCoordenador = coordenadorRepository.save(novoCoordenador);
        return ResponseEntity.ok().body(savedCoordenador);
    }

    @GetMapping
    public ResponseEntity getCoordenadores(){
        List<Coordenador> coordenadores = coordenadorRepository.findAll();
        return ResponseEntity.ok().body(coordenadores);
    }

    @PostMapping("/{id}/definir-inicio")
    public ResponseEntity definirDataInicio(@PathVariable(value = "id") Long id, @RequestBody DataInicio dataInicio) {
        LocalDateTime dataHoraInicio = dataInicio.getDateTime();

        Optional<Coordenador> optionalCoordenador = coordenadorRepository.findById(id);

        if (optionalCoordenador.isPresent()) {
            Coordenador coordenador = optionalCoordenador.get();
            coordenador.setDataInicio(dataHoraInicio);
            coordenadorRepository.save(coordenador);
            return ResponseEntity.ok().body("salvo");

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/definir-final")
    public ResponseEntity definirDataFinal(@PathVariable(value = "id") Long id, @RequestBody DataFinal dataFinal) {
        LocalDateTime dataHoraFinal = dataFinal.getDateTime();

        Optional<Coordenador> optionalCoordenador = coordenadorRepository.findById(id);

        if (optionalCoordenador.isPresent()) {
            Coordenador coordenador = optionalCoordenador.get();
            coordenador.setDataFinal(dataHoraFinal);
            coordenadorRepository.save(coordenador);
            return ResponseEntity.ok().body("salvo");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}