package com.decrypto.api.decrypto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.repository.ComitenteRepository;

@Service
public class ComitenteService {
    
    @Autowired
    private ComitenteRepository comitenteRepository;
    
    public Comitente saveComitente(Comitente comitente) {
        Optional<Comitente> existing = comitenteRepository.findByDescripcion(comitente.getDescripcion());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("El comitente ya existe.");
        }
        return comitenteRepository.save(comitente);
    }

    public List<Comitente> getAllComitentes() {
        return comitenteRepository.findAll();
    }
    

    public List<StatsDTO> getStats() {
        List<StatsDTO> stats = new ArrayList<>();

        // Obtener comitentes y sus mercados
        List<Comitente> comitentes = comitenteRepository.findAll();

        // Agrupar por país y mercado
        Map<String, Map<String, Double>> countryMarketMap = new HashMap<>();

        for (Comitente comitente : comitentes) {
            for (Mercado mercado : comitente.getMercados()) {
                String countryName = mercado.getPais().getNombre().toString();
                String marketCode = mercado.getCodigo(); // Obtener el código del mercado
                Double percentage = mercado.getPorcentaje(); // Obtener el porcentaje

                // Agregar datos al mapa
                countryMarketMap
                    .computeIfAbsent(countryName, k -> new HashMap<>())
                    .put(marketCode, percentage);
            }
        }

        // Construir la lista de StatsDTO
        for (Map.Entry<String, Map<String, Double>> entry : countryMarketMap.entrySet()) {
            String country = entry.getKey();
            Map<String, Double> marketData = entry.getValue();
            List<Map<String, Map<String, String>>> marketStats = new ArrayList<>(); // Cambiamos aquí

            for (Map.Entry<String, Double> marketEntry : marketData.entrySet()) {
                String marketName = marketEntry.getKey();
                Double marketPercentage = marketEntry.getValue();

                // Crear un mapa para cada mercado
                Map<String, Map<String, String>> marketMap = new HashMap<>();
                Map<String, String> percentageMap = new HashMap<>();
                percentageMap.put("percentage", String.valueOf(marketPercentage)); // Convertir a String
                marketMap.put(marketName, percentageMap);
                marketStats.add(marketMap); // Agregar el mapa del mercado a la lista
            }

            stats.add(new StatsDTO(country, marketStats)); // Agregar a la lista de resultados
        }

        return stats;
    }



    /*private Map<String, Double> calcularPorcentajes(List<Comitente> comitentes) {
        // Lógica para calcular el porcentaje de comitentes por mercado
        Map<String, Long> countByMarket = comitentes.stream()
            .flatMap(comitente -> comitente.getMercados().stream())
            .collect(Collectors.groupingBy(Mercado::getCodigo, Collectors.counting()));

        long totalComitentes = comitentes.size();

        return countByMarket.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> (entry.getValue() * 100.0) / totalComitentes
            ));
    }*/

    public List<Comitente> getComitentesByPais(NombrePais pais) {
        return comitenteRepository.findByPais(pais);
    }

}
