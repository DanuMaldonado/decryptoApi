package com.decrypto.api.decrypto.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.ComitenteDTO;
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
