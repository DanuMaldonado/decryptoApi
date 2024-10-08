package com.decrypto.api.decrypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.StatsDTO;
import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.ComitenteRepository;
import com.decrypto.api.decrypto.repository.MercadoRepository;
import com.decrypto.api.decrypto.repository.PaisRepository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StatsService {

	@Autowired
    private ComitenteRepository comitenteRepository;

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private PaisRepository paisRepository;
    
    
    public void actualizarPorcentajes() {
        getStats();
    }
    
    public List<StatsDTO> getStats() {
        List<StatsDTO> stats = new ArrayList<>();
        List<Comitente> comitentes = comitenteRepository.findAll();

        // 1. Calcular el total de comitentes por país
        Map<NombrePais, Long> comitentesPorPais = comitentes.stream()
            .flatMap(comitente -> comitente.getMercados().stream())
            .collect(Collectors.groupingBy(mercado -> mercado.getPais().getNombre(), Collectors.counting()));

        // 2. Calcular el total de comitentes (todos los países)
        long totalComitentes = comitentes.size();

        // 3. Calcular el porcentaje de comitentes por país
        Map<NombrePais, Double> porcentajePorPais = new HashMap<>();
        for (Map.Entry<NombrePais, Long> entry : comitentesPorPais.entrySet()) {
            double porcentajePais = (entry.getValue() * 100.0) / totalComitentes;
            porcentajePorPais.put(entry.getKey(), porcentajePais);
        }

        // 4. Calcular el porcentaje por mercado en cada país
        Map<NombrePais, Map<String, Double>> mercadoPorcentajes = new HashMap<>();
        for (Comitente comitente : comitentes) {
            for (Mercado mercado : comitente.getMercados()) {
                NombrePais pais = mercado.getPais().getNombre();
                String codigoMercado = mercado.getCodigo();

                // Inicializar mapa de mercado para el país si es necesario
                mercadoPorcentajes.computeIfAbsent(pais, k -> new HashMap<>());

                // Incrementar el contador de comitentes por mercado
                mercadoPorcentajes.get(pais).merge(codigoMercado, 1.0, Double::sum);
            }
        }

        // 5. Ajustar los porcentajes de mercados dentro del porcentaje total del país
        for (Map.Entry<NombrePais, Map<String, Double>> entry : mercadoPorcentajes.entrySet()) {
            NombrePais pais = entry.getKey();
            Map<String, Double> mercados = entry.getValue();
            double porcentajeTotalPais = porcentajePorPais.get(pais);
            Pais paisEntity = paisRepository.findByNombre(pais);

            // Construir lista de mercados con su porcentaje ajustado
            List<Map<String, Map<String, String>>> mercadoStats = new ArrayList<>();
            for (Map.Entry<String, Double> mercadoEntry : mercados.entrySet()) {
                String nombreMercado = mercadoEntry.getKey();
                
                Mercado mercado = mercadoRepository.findByCodigoAndPais(nombreMercado, paisEntity);


                if (mercado != null) {
                    double porcentajeMercado = (mercadoEntry.getValue() * porcentajeTotalPais) / comitentesPorPais.get(pais);
                    mercado.setPorcentaje(porcentajeMercado);
                    mercadoRepository.save(mercado);

                    Map<String, String> porcentajeMap = new HashMap<>();
                    porcentajeMap.put("percentage", String.format("%.2f", porcentajeMercado));

                    Map<String, Map<String, String>> mercadoMap = new HashMap<>();
                    mercadoMap.put(nombreMercado, porcentajeMap);
                    mercadoStats.add(mercadoMap);
                } else {
                    System.out.println("Mercado no encontrado para código: " + nombreMercado + " y país: " + pais);
                }
            }

            stats.add(new StatsDTO(pais.name(), mercadoStats));
        }

        return stats;
    }


}


