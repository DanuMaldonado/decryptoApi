package com.decrypto.api.decrypto.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.decrypto.api.decrypto.dto.MercadoDTO;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.service.MercadoService;
import com.decrypto.api.decrypto.service.PaisService;

@RestController
@RequestMapping("/api/mercados")
public class MercadoController {

    @Autowired
    private MercadoService mercadoService;
    
    @Autowired
    private PaisService paisService;

    // Obtener todos los mercados (GET)
    @GetMapping
    public ResponseEntity<List<Mercado>> getAllMercados() {
        List<Mercado> mercados = mercadoService.findAll();
        return ResponseEntity.ok(mercados);
    }

    // Obtener un mercado por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Mercado> getMercadoById(@PathVariable Long id) {
        Optional<Mercado> mercado = mercadoService.findById(id);
        return mercado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo mercado (POST)
    @PostMapping
    public ResponseEntity<MercadoDTO> createMercado(@RequestBody MercadoDTO mercadoDTO) {
        MercadoDTO savedMercadoDTO = mercadoService.save(mercadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMercadoDTO);
        
    }
   
    // Actualizar un mercado existente (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<MercadoDTO> updateMercado(@PathVariable Long id, @RequestBody MercadoDTO mercadoDTO) {
        // Buscar el mercado existente por ID
        Optional<Mercado> existingMercado = mercadoService.findById(id);
        
        if (!existingMercado.isPresent()) {
            return ResponseEntity.notFound().build(); // Retornar 404 si no se encuentra
        }

        // Actualizar el mercado existente con los nuevos datos del DTO
        Mercado mercadoToUpdate = existingMercado.get();
        mercadoToUpdate.setCodigo(mercadoDTO.getCodigo());
        mercadoToUpdate.setDescripcion(mercadoDTO.getDescripcion());
        
        // Verificar el país por ID
        Optional<Pais> paisOptional = paisService.getPaisById(mercadoDTO.getPaisId());
        if (!paisOptional.isPresent()) {
            return ResponseEntity.badRequest().body(null); // Retornar 400 si el país no se encuentra
        }

        // Asignar el país
        mercadoToUpdate.setPais(paisOptional.get());
        mercadoToUpdate.setPorcentaje(mercadoDTO.getPorcentaje());

        // Guardar el mercado actualizado
        MercadoDTO updatedMercadoDTO = mercadoService.save(mercadoDTO);

        return ResponseEntity.ok(updatedMercadoDTO); // Retornar el mercado actualizado
    }


    // Eliminar un mercado por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMercado(@PathVariable Long id) {
        Optional<Mercado> existingMercado = mercadoService.findById(id);
        if (!existingMercado.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        mercadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
