package com.decrypto.api.decrypto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.dto.ComitenteDTO;
import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.service.ComitenteService;
import com.decrypto.api.decrypto.service.MercadoService;

@RestController
@RequestMapping("/api/comitente")
public class ComitenteController {
    
    @Autowired
    private ComitenteService comitenteService;
    
    @Autowired
    private MercadoService mercadoService;
    
    public ComitenteController(ComitenteService comitenteService) {
        this.comitenteService = comitenteService;
    }
    
    @GetMapping
    public List<Comitente> getAllComitentes() {
        return comitenteService.getAllComitentes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comitente> getComitenteById(@PathVariable Long id) {
        return comitenteService.getComitenteById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    
    @PostMapping
    public ResponseEntity<ComitenteDTO> createComitente(@RequestBody ComitenteDTO comitenteDTO) {
        ComitenteDTO savedComitenteDTO = comitenteService.save(comitenteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComitenteDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Comitente> updateComitente(@PathVariable Long id, @RequestBody ComitenteDTO comitenteDTO) {
    	Optional<Comitente> existingComitente = comitenteService.getComitenteById(id);
        if (!comitenteService.getComitenteById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Comitente comitente = existingComitente.get();
        comitente.setDescripcion(comitenteDTO.getDescripcion());
        
        List<Mercado> mercados = mercadoService.findByIds(comitenteDTO.getMercadoIds());
        comitente.setMercados(mercados);

        Comitente updatedComitente = comitenteService.save(comitente);
        return ResponseEntity.ok(updatedComitente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComitente(@PathVariable Long id) {
        if (!comitenteService.getComitenteById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comitenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/pais/{pais}")
    public ResponseEntity<List<Comitente>> getComitentesByPais(@PathVariable NombrePais pais) {
        List<Comitente> comitentes = comitenteService.getComitentesByPais(pais);
        if (comitentes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(comitentes);
    }
    

}