package com.tcc.controller;

import com.tcc.dtos.request.coordenador.DataFinal;
import com.tcc.dtos.request.coordenador.CoordenadorRequest;
import com.tcc.dtos.request.coordenador.DataInicio;
import com.tcc.models.Coordenador;
import com.tcc.repository.CoordenadorRepository;
import com.tcc.service.CoordenadorService;
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
    CoordenadorService coordenadorService;

    @PostMapping
    public ResponseEntity cadastrarCoordenador(@RequestBody @Valid CoordenadorRequest coordenadorRequest){
        return coordenadorService.registerCoordenador(coordenadorRequest);
    }

    @GetMapping
    public ResponseEntity getCoordenadores(){
        return coordenadorService.getAllCoordenadores();
    }

    @PostMapping("/{id}/definir-inicio")
    public ResponseEntity definirDataInicio(@PathVariable(value = "id") Long id, @RequestBody DataInicio dataInicio) {
        return coordenadorService.setStartDate(id, dataInicio);
    }

    @PostMapping("/{id}/definir-final")
    public ResponseEntity definirDataFinal(@PathVariable(value = "id") Long id, @RequestBody DataFinal dataFinal) {
        return coordenadorService.setFinalDate(id, dataFinal);
    }
}