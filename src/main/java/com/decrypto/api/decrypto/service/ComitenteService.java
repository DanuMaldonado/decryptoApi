package com.decrypto.api.decrypto.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

@Service
public class ComitenteService {
    
    private final ComitenteRepository comitenteRepository;
    private final MercadoRepository mercadoRepository;
    
    public ComitenteService(ComitenteRepository comitenteRepository, MercadoRepository mercadoRepository) {
        this.comitenteRepository = comitenteRepository;
        this.mercadoRepository = mercadoRepository;
    }
    
    public ComitenteDTO save(ComitenteDTO comitenteDTO) {
        List<Mercado> mercados = comitenteDTO.getMercadoIds().stream()
            .map(id -> mercadoRepository.findById(id).orElseThrow(() -> new RuntimeException("Mercado no encontrado")))
            .collect(Collectors.toList());
        
        Comitente comitente = new Comitente(comitenteDTO.getDescripcion(), mercados);
        Comitente savedComitente = comitenteRepository.save(comitente);

        return new ComitenteDTO(savedComitente.getId(), savedComitente.getDescripcion(),
                savedComitente.getMercados().stream().map(Mercado::getId).collect(Collectors.toList()));
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

    public List<Comitente> getComitentesByPais(NombrePais pais) {
        return comitenteRepository.findByPais(pais);
    }
    
    public Optional<Comitente> getComitenteById(Long id) {
        return comitenteRepository.findById(id);
    }
    
    public List<Comitente> getAllComitentes() {
        return comitenteRepository.findAll();
    }

    public Comitente save(Comitente comitente) {
        if (comitenteRepository.findByDescripcion(comitente.getDescripcion()).isPresent()) {
            throw new RuntimeException("Comitente con esta descripción ya existe.");
        }
        return comitenteRepository.save(comitente);
    }

	public void deleteById(Long id) {
		comitenteRepository.deleteById(id);
	}

}
