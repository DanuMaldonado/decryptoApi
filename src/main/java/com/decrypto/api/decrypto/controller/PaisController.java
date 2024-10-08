package com.decrypto.api.decrypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.service.PaisService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
	
	@Autowired
	private PaisService paisService;
	
	
	 /**
     * Obtiene todos los países.
     * @return Lista de todos los países.
     */
    @Operation(summary = "Obtener todos los países", description = "Devuelve una lista de todos los países disponibles.")
    @ApiResponse(responseCode = "200", description = "Devuelve listado de paises en formato Json")
    @GetMapping
    public List<Pais> getAllPaises() {
        return paisService.getAllPaises();
    }
	
    
    /**
     * Obtiene un país por su ID.
     * @param id ID del país.
     * @return País si existe, o un error 404 si no se encuentra.
     */
    @Operation(summary = "Obtener un país por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País encontrado"),
        @ApiResponse(responseCode = "404", description = "País no encontrado")
    })
	@GetMapping("/{id}")
	public ResponseEntity<Pais> getPaisById(@PathVariable Long id) {
	   return paisService.getPaisById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	  }

}
