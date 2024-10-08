package com.decrypto.api.decrypto.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.ComitenteDTO;
import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.repository.ComitenteRepository;
import com.decrypto.api.decrypto.repository.MercadoRepository;


import io.swagger.v3.oas.annotations.Operation; // Importa para OpenAPI
import io.swagger.v3.oas.annotations.Parameter; // Importa para OpenAPI
import io.swagger.v3.oas.annotations.responses.ApiResponse; // Importa para OpenAPI
import io.swagger.v3.oas.annotations.responses.ApiResponses; // Importa para OpenAPI

@Service
public class ComitenteService {
    
    private final ComitenteRepository comitenteRepository;
    private final MercadoRepository mercadoRepository;
    private final StatsService statsService;
    
    public ComitenteService(ComitenteRepository comitenteRepository, MercadoRepository mercadoRepository, StatsService statsService) {
        this.comitenteRepository = comitenteRepository;
        this.mercadoRepository = mercadoRepository;
        this.statsService = statsService;
    }
    
    @Operation(summary = "Crear un nuevo Comitente", description = "Crea un Comitente y actualiza los porcentajes de los mercados.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comitente creado con éxito"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado")
    })
    public ComitenteDTO createComitente(@Parameter(description = "Datos del Comitente a crear")ComitenteDTO comitenteDTO) {
    	 // Mapeo de IDs de mercados a objetos Mercado
        List<Mercado> mercados = comitenteDTO.getMercadoIds().stream()
            .map(id -> mercadoRepository.findById(id).
            		orElseThrow(() -> new RuntimeException("Mercado no encontrado")))
            .collect(Collectors.toList());
        
        Comitente comitente = new Comitente(comitenteDTO.getDescripcion(), mercados);
        Comitente savedComitente = comitenteRepository.save(comitente);

        return new ComitenteDTO(savedComitente.getId(), savedComitente.getDescripcion(),
                savedComitente.getMercados().stream().map(Mercado::getId).collect(Collectors.toList()));
    }
  
    @Operation(summary = "Obtener Comitentes por país", description = "Retorna una lista de Comitentes asociados a un país específico.")
    public List<Comitente> getComitentesByPais(@Parameter(description = "Nombre del país") NombrePais pais) {
        return comitenteRepository.findByPais(pais);
    }
    
    @Operation(summary = "Obtener un Comitente por ID", description = "Retorna un Comitente basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comitente encontrado"),
        @ApiResponse(responseCode = "404", description = "Comitente no encontrado")
    })
    public Optional<Comitente> getComitenteById(@Parameter(description = "ID del Comitente a buscar")Long id) {
        return comitenteRepository.findById(id);
    }
    
    @Operation(summary = "Obtener todos los Comitentes", description = "Retorna una lista de todos los Comitentes.")
    public List<Comitente> getAllComitentes() {
        return comitenteRepository.findAll();
    }

    @Operation(summary = "Actualizar un Comitente", description = "Actualiza la información de un Comitente existente.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comitente actualizado con éxito"),
        @ApiResponse(responseCode = "409", description = "Conflicto: Comitente con esta descripción ya existe.")
    })
    public Comitente updateComitente(@Parameter(description = "Comitente con la información actualizada") Comitente comitente) {
        if (comitenteRepository.findByDescripcion(comitente.getDescripcion()).isPresent()) {
            throw new RuntimeException("Comitente con esta descripción ya existe.");
        }
        statsService.actualizarPorcentajes();
        return comitenteRepository.save(comitente);   
    }
    
    @Operation(summary = "Eliminar un Comitente", description = "Elimina un Comitente basado en su ID.")
	public void deleteById(Long id) {
		comitenteRepository.deleteById(id);
	}
    
    @Operation(summary = "Obtener estadísticas de Comitentes", description = "Retorna estadísticas relacionadas con los Comitentes.")
	public List<StatsDTO> getStats() {
        return statsService.getStats();
    }
	

}

