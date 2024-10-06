package com.decrypto.api.decrypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.repository.ComitenteRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {

    @Autowired
    private ComitenteRepository comitenteRepository;

    public List<StatsDTO> getStats() {
        List<StatsDTO> stats = new ArrayList<>();

        // Obtener comitentes y sus mercados
        List<Comitente> comitentes = comitenteRepository.findAll();

        // Agrupar por país y mercado
        Map<String, Map<String, Double>> countryMarketMap = new HashMap<>();

        for (Comitente comitente : comitentes) {
            for (Mercado mercado : comitente.getMercados()) {
                String countryName = mercado.getPais().getNombre().name();
                String marketCode = mercado.getCodigo();

                // Contar cuántos comitentes hay por país y mercado
                countryMarketMap
                    .computeIfAbsent(countryName, k -> new HashMap<>())
                    .put(marketCode, countryMarketMap.getOrDefault(countryName, new HashMap<>()).getOrDefault(marketCode, 0.0) + 1);
            }
        }

        // Construir la lista de StatsDTO
        for (Map.Entry<String, Map<String, Double>> entry : countryMarketMap.entrySet()) {
            String country = entry.getKey();
            Map<String, Double> marketData = entry.getValue();
            List<Map<String, Map<String, String>>> marketStats = new ArrayList<>();

            for (Map.Entry<String, Double> marketEntry : marketData.entrySet()) {
                String marketName = marketEntry.getKey();
                Double comitenteCount = marketEntry.getValue();

                // Crear un mapa para cada mercado
                Map<String, Map<String, String>> marketMap = new HashMap<>();
                Map<String, String> percentageMap = new HashMap<>();
                
                // Calcular el porcentaje
                double totalComitentes = comitenteRepository.count(); 
                double percentage = (comitenteCount / totalComitentes) * 100;

                percentageMap.put("percentage", String.format("%.2f", percentage)); 
                marketMap.put(marketName, percentageMap);
                marketStats.add(marketMap); // Agregar el mapa del mercado a la lista
            }

            stats.add(new StatsDTO(country, marketStats)); // Agregar a la lista de resultados
        }

        return stats;
    }
}

