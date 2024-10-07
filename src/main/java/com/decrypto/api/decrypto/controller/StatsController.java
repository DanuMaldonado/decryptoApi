package com.decrypto.api.decrypto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.service.ComitenteService;

import java.util.List;

@RestController
public class StatsController {
    
    private final ComitenteService comitenteService;

    public StatsController(ComitenteService comitenteService) {
        this.comitenteService = comitenteService;
    }

    //@GetMapping("/stats")
    //public List<StatsDTO> getStatsdeprecated() {
    //    return statsService.getStats(); // Llamar al servicio para obtener las estadísticas
   // }
    
    @GetMapping("/stats")
    public ResponseEntity<List<StatsDTO>> getStats() {
    	List<StatsDTO> stats = comitenteService.getStats();
        return ResponseEntity.ok(stats);
    }
}
