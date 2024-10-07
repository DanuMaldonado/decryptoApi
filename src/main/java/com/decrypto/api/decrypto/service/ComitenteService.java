package com.decrypto.api.decrypto.service;


import java.util.List;
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
    private final StatsService statsService;
    
    public ComitenteService(ComitenteRepository comitenteRepository, MercadoRepository mercadoRepository, StatsService statsService) {
        this.comitenteRepository = comitenteRepository;
        this.mercadoRepository = mercadoRepository;
        this.statsService = statsService;
    }
    
    public ComitenteDTO save(ComitenteDTO comitenteDTO) {
        List<Mercado> mercados = comitenteDTO.getMercadoIds().stream()
            .map(id -> mercadoRepository.findById(id).
            		orElseThrow(() -> new RuntimeException("Mercado no encontrado")))
            .collect(Collectors.toList());
        
        Comitente comitente = new Comitente(comitenteDTO.getDescripcion(), mercados);
        Comitente savedComitente = comitenteRepository.save(comitente);

       // statsService.actualizarPorcentajes();
        return new ComitenteDTO(savedComitente.getId(), savedComitente.getDescripcion(),
                savedComitente.getMercados().stream().map(Mercado::getId).collect(Collectors.toList()));
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
		//statsService.actualizarPorcentajes();
		comitenteRepository.deleteById(id);
	}
	
	public List<StatsDTO> getStats() {
        return statsService.getStats();
    }
	
	/*public List<StatsDTO> getStats() {
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

	        // Construir lista de mercados con su porcentaje ajustado
	        List<Map<String, Map<String, String>>> mercadoStats = new ArrayList<>();
	        for (Map.Entry<String, Double> mercadoEntry : mercados.entrySet()) {
	            String nombreMercado = mercadoEntry.getKey();
	            double porcentajeMercado = (mercadoEntry.getValue() * porcentajeTotalPais) / comitentesPorPais.get(pais);

	            // Crear estructura para la respuesta
	            Map<String, String> porcentajeMap = new HashMap<>();
	            porcentajeMap.put("percentage", String.format("%.2f", porcentajeMercado));

	            Map<String, Map<String, String>> mercadoMap = new HashMap<>();
	            mercadoMap.put(nombreMercado, porcentajeMap);

	            mercadoStats.add(mercadoMap);
	        }

	        // Crear StatsDTO para el país
	        stats.add(new StatsDTO(pais.name(), mercadoStats));
	    }

	    return stats;
	}*/

}

