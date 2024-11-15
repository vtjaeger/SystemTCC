package com.tcc.controller;

import com.tcc.dtos.request.coordenador.DataInicio;
import com.tcc.service.CoordenadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coordenadores")
@CrossOrigin(origins = "*")
public class CoordenadorController {
    @Autowired
    CoordenadorService coordenadorService;

    @GetMapping
    public ResponseEntity getCoordenadores(){
        return coordenadorService.getAllCoordenadores();
    }

    @PostMapping("/{id}/data")
    public ResponseEntity setDate(@PathVariable(value = "id") Long id, @RequestBody DataInicio dataInicio) {
        return coordenadorService.setDate(id, dataInicio);
    }

}