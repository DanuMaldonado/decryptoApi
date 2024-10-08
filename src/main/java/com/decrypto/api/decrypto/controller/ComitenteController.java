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
import com.decrypto.api.decrypto.service.ComitenteService;
import com.decrypto.api.decrypto.service.MercadoService;
import com.decrypto.api.decrypto.service.StatsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/comitentes")
public class ComitenteController {
    
    @Autowired
    private ComitenteService comitenteService;
    
    @Autowired
    private MercadoService mercadoService;
    
    @Autowired
    private StatsService statsService;
    
    public ComitenteController(ComitenteService comitenteService) {
        this.comitenteService = comitenteService;
    }
    
    /**
     * Obtiene todos los comitentes.
     * @return Lista de comitentes.
     */
    @Operation(summary = "Obtener todos los comitentes")
    @ApiResponse(responseCode = "200", description = "Devuelve listado de comitentes en formato Json")
    @GetMapping
    public List<Comitente> getAllComitentes() {
    	statsService.actualizarPorcentajes();
        return comitenteService.getAllComitentes();
    }

    /**
     * Obtiene un comitente por su ID.
     * @param id ID del comitente.
     * @return Comitente si existe, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Obtener un comitente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comitente encontrado"),
        @ApiResponse(responseCode = "404", description = "Comitente no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Comitente> getComitenteById(@PathVariable Long id) {
        return comitenteService.getComitenteById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    
    /**
     * Crea un nuevo comitente.
     * @param comitenteDTO Datos del comitente a crear.
     * @return Comitente creado.
     */
    @Operation(summary = "Crear un nuevo comitente")
    @ApiResponse(responseCode = "201", description = "Comitente creado exitosamente")
    @PostMapping
    public ResponseEntity<ComitenteDTO> createComitente(@RequestBody ComitenteDTO comitenteDTO) {
        ComitenteDTO savedComitenteDTO = comitenteService.createComitente(comitenteDTO);
        statsService.actualizarPorcentajes();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedComitenteDTO);
    }
    
    /**
     * Actualiza un comitente existente.
     * @param id ID del comitente a actualizar.
     * @param comitenteDTO Datos actualizados del comitente.
     * @return Comitente actualizado, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Actualizar un comitente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comitente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comitente no encontrado")
    })
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

        Comitente updatedComitente = comitenteService.updateComitente(comitente);
        statsService.actualizarPorcentajes();
        return ResponseEntity.ok(updatedComitente);
    }

    /**
     * Elimina un comitente por su ID.
     * @param id ID del comitente.
     * @return Respuesta vacía si se elimina correctamente, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Eliminar un comitente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Comitente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Comitente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComitente(@PathVariable Long id) {
        if (!comitenteService.getComitenteById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comitenteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    

}