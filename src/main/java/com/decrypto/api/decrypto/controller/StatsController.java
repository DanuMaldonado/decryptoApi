package com.decrypto.api.decrypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.service.StatsService;

import java.util.List;

@RestController
public class StatsController {

    @Autowired
    private StatsService statsService;

    @GetMapping("/stats")
    public List<StatsDTO> getStats() {
        return statsService.getStats(); // Llamar al servicio para obtener las estadísticas
    }
}
