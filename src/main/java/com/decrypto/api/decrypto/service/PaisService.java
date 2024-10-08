package com.decrypto.api.decrypto.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.PaisRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse; 

@Service
public class PaisService {
	
	@Autowired
	private PaisRepository  paisRepository;
	
	@Operation(summary = "Obtener un País por ID", description = "Retorna un País basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "País encontrado"),
        @ApiResponse(responseCode = "404", description = "País no encontrado")
    })
	 public Optional<Pais> getPaisById(Long id) {
	        return paisRepository.findById(id);
	    }

    @Operation(summary = "Obtener todos los Países", description = "Retorna una lista de todos los Países.")
	public List<Pais> getAllPaises() {
		return paisRepository.findAll();
	}

}
