package com.decrypto.api.decrypto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.MarketStatsDTO;
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
    
    
    public List<StatsDTO> getComitenteStats() {
        List<StatsDTO> stats = new ArrayList<>();

        // Supongamos que tenemos listas de comitentes por país y mercado ya obtenidas
        List<Comitente> comitentesArgentina = comitenteRepository.findByPais(NombrePais.ARGENTINA);
        List<Comitente> comitentesUruguay = comitenteRepository.findByPais(NombrePais.URUGUAY);

        // Calcular porcentaje para Argentina
        Map<String, Double> argentinaMarketStats = calcularPorcentajes(comitentesArgentina);
        List<MarketStatsDTO> argentinaMarketDTOs = argentinaMarketStats.entrySet().stream()
            .map(entry -> new MarketStatsDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        stats.add(new StatsDTO("Argentina", argentinaMarketDTOs));

        // Calcular porcentaje para Uruguay
        Map<String, Double> uruguayMarketStats = calcularPorcentajes(comitentesUruguay);
        List<MarketStatsDTO> uruguayMarketDTOs = uruguayMarketStats.entrySet().stream()
            .map(entry -> new MarketStatsDTO(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        stats.add(new StatsDTO("Uruguay", uruguayMarketDTOs));

        return stats;
    }

    private Map<String, Double> calcularPorcentajes(List<Comitente> comitentes) {
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
    }

    public List<Comitente> getComitentesByPais(NombrePais pais) {
        return comitenteRepository.findByPais(pais);
    }

}
