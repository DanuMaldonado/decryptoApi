package com.decrypto.api.decrypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.service.ComitenteService;

@RestController
@RequestMapping("/api/comitentes")
public class ComitenteController {
    
    @Autowired
    private ComitenteService comitenteService;

    @PostMapping
    public ResponseEntity<Comitente> createComitente(@RequestBody Comitente comitente) {
        Comitente created = comitenteService.saveComitente(comitente);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Comitente>> getComitentesByPais(@PathVariable NombrePais pais) {
        List<Comitente> comitentes = comitenteService.getComitentesByPais(pais);
        if (comitentes.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si no hay contenido
        }
        return ResponseEntity.ok(comitentes);
    }


}