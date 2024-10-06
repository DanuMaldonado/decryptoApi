package com.decrypto.api.decrypto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.MercadoDTO;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.MercadoRepository;

@Service
public class MercadoService {
	
    @Autowired
    private MercadoRepository mercadoRepository;
    
    @Autowired
    private PaisService paisService;

    public List<Mercado> findAll() {
        return mercadoRepository.findAll();
    }

    public Optional<Mercado> findById(Long id) {
        return mercadoRepository.findById(id);
    }
    
    public List<Mercado> findByIds(List<Long> ids) {
        return ids.stream()
                  .map(mercadoRepository::findById)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList());
    }

    public MercadoDTO save(MercadoDTO mercadoDTO) {
    	
    	
    	
        // Buscar el país por ID desde el servicio PaisService
        Optional<Pais> paisOptional = paisService.getPaisById(mercadoDTO.getPaisId());
        
        if (!paisOptional.isPresent()) {
            throw new RuntimeException("País no encontrado.");
        }

        Pais pais = paisOptional.get();

        // Verificar que el país sea válido (Argentina o Uruguay)
        if (NombrePais.ARGENTINA.equals(pais.getNombre()) && NombrePais.URUGUAY.equals(pais.getNombre())) {
            throw new RuntimeException("El país debe ser Argentina o Uruguay.");
        }

        // Crear una nueva instancia de Mercado a partir del DTO
        Mercado mercado = new Mercado();
        mercado.setCodigo(mercadoDTO.getCodigo());
        mercado.setDescripcion(mercadoDTO.getDescripcion());
        mercado.setPais(pais); // Asignar el país encontrado
        mercado.setPorcentaje(mercadoDTO.getPorcentaje());

        // Guardar y devolver el mercado
        // Guardar el mercado
        Mercado savedMercado = mercadoRepository.save(mercado);

        // Convertir el Mercado guardado a un MercadoDTO
        MercadoDTO savedMercadoDTO = new MercadoDTO();
        savedMercadoDTO.setId(savedMercado.getId());
        savedMercadoDTO.setCodigo(savedMercado.getCodigo());
        savedMercadoDTO.setDescripcion(savedMercado.getDescripcion());
        savedMercadoDTO.setPaisId(savedMercado.getPais().getId()); // Obtener el ID del país
        savedMercadoDTO.setPorcentaje(savedMercado.getPorcentaje());

        return savedMercadoDTO;

    }

    public void deleteById(Long id) {
        mercadoRepository.deleteById(id);
    }
}

