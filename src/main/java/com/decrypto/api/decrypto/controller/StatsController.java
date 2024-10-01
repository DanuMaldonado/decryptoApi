package com.decrypto.api.decrypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.dto.CountryStatsDTO;
import com.decrypto.api.decrypto.service.ComitenteService;

@RestController
@RequestMapping("/api")
public class StatsController {
    
    @Autowired
    private ComitenteService comitenteService;

    @GetMapping("/stats")
    public ResponseEntity<List<CountryStatsDTO>> getStats() {
        List<CountryStatsDTO> stats = comitenteService.calculateStatistics();
        return ResponseEntity.ok(stats);
    }
    
}