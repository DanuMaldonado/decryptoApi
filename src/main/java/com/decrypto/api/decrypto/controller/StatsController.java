package com.decrypto.api.decrypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.service.ComitenteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.util.List;

@RestController
public class StatsController {
    
    private final ComitenteService comitenteService;

    public StatsController(ComitenteService comitenteService) {
        this.comitenteService = comitenteService;
    }

    /**
     * Obtiene las estadísticas de distribución de comitentes por país y mercado.
     * @return Lista de estadísticas con porcentajes por país y mercado.
     */
    @Operation(summary = "Obtener estadísticas", description = "Devuelve estadísticas de distribución de comitentes por país y mercado.")
    @ApiResponse(responseCode = "200", description = "Devuelve el listado totalizado de los comitentes por pais y mercado")
    @GetMapping("/stats")
    public ResponseEntity<List<StatsDTO>> getStats() {
    	List<StatsDTO> stats = comitenteService.getStats();
        return ResponseEntity.ok(stats);
    }
}
