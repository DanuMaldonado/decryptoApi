package com.decrypto.api.decrypto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.CountryStatsDTO;
import com.decrypto.api.decrypto.dto.MarketStatsDTO;
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
    
   
    public List<CountryStatsDTO> calculateStatistics() {
        List<Comitente> comitentes = getAllComitentes(); // Obtener comitentes

        Map<String, Map<String, Double>> statsMap = new HashMap<>();

        // Calcular las estadísticas
        for (Comitente comitente : comitentes) {
            for (Mercado mercado : comitente.getMercados()) {
                String country = mercado.getPais().getNombre().toString();
                String marketCode = mercado.getCodigo();

                statsMap.putIfAbsent(country, new HashMap<>());
                statsMap.get(country).put(marketCode, statsMap.get(country).getOrDefault(marketCode, 0.0) + 1);
            }
        }

        // Calcular porcentajes
        List<CountryStatsDTO> countryStats = new ArrayList<>();
        for (Map.Entry<String, Map<String, Double>> entry : statsMap.entrySet()) {
            String country = entry.getKey();
            Map<String, Double> marketCounts = entry.getValue();

            double total = marketCounts.values().stream().mapToDouble(Double::doubleValue).sum();
            List<Map<String, MarketStatsDTO>> marketList = new ArrayList<>();

            for (Map.Entry<String, Double> marketEntry : marketCounts.entrySet()) {
               
                double percentage = (marketEntry.getValue() / total) * 100;

                Map<String, MarketStatsDTO> marketStats = new HashMap<>();
                marketStats.put(country, new MarketStatsDTO(percentage));
                marketList.add(marketStats);
            }

            countryStats.add(new CountryStatsDTO(country, marketList));
        }

        return countryStats;
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
