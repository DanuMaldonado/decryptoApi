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
import com.decrypto.api.decrypto.service.StatsService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/mercados")
public class MercadoController {

    @Autowired
    private MercadoService mercadoService;
    
    @Autowired
    private PaisService paisService;
    
    @Autowired
    private StatsService statsService;

    /**
     * Obtiene todos los mercados.
     * @return Lista de todos los mercados.
     */
    @Operation(summary = "Obtener todos los mercados", description = "Devuelve una lista de todos los mercados disponibles.")
    @ApiResponse(responseCode = "200", description = "Devuelve lista de mercados en formato Json")
    @GetMapping
    public ResponseEntity<List<Mercado>> getAllMercados() {
        List<Mercado> mercados = mercadoService.findAll();
        return ResponseEntity.ok(mercados);
    }

    /**
     * Obtiene un mercado por su ID.
     * @param id ID del mercado.
     * @return Mercado si existe, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Obtener un mercado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado encontrado"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mercado> getMercadoById(@PathVariable Long id) {
        Optional<Mercado> mercado = mercadoService.findById(id);
        return mercado.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Crea un nuevo mercado.
     * @param mercadoDTO Datos del mercado a crear.
     * @return Mercado creado.
     */
    @Operation(summary = "Crear un nuevo mercado")
    @ApiResponse(responseCode = "201", description = "Mercado creado exitosamente")
    @PostMapping
    public ResponseEntity<MercadoDTO> createMercado(@RequestBody MercadoDTO mercadoDTO) {
        MercadoDTO savedMercadoDTO = mercadoService.save(mercadoDTO);
        statsService.actualizarPorcentajes();
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMercadoDTO);
        
    }
   
    /**
     * Actualiza un mercado existente.
     * @param id ID del mercado a actualizar.
     * @param mercadoDTO Datos actualizados del mercado.
     * @return Mercado actualizado, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Actualizar un mercado existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado"),
        @ApiResponse(responseCode = "400", description = "País no encontrado")
    })
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


    /**
     * Elimina un mercado por su ID.
     * @param id ID del mercado.
     * @return Respuesta vacía si se elimina correctamente, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Eliminar un mercado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mercado eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMercado(@PathVariable Long id) {
        Optional<Mercado> existingMercado = mercadoService.findById(id);
        if (!existingMercado.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        statsService.actualizarPorcentajes();
        mercadoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
