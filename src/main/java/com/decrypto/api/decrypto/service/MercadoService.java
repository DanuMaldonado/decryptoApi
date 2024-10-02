package com.decrypto.api.decrypto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.repository.MercadoRepository;

@Service
public class MercadoService {
	
    @Autowired
    private MercadoRepository mercadoRepository;

    public List<Mercado> findAll() {
        return mercadoRepository.findAll();
    }

    public Optional<Mercado> findById(Long id) {
        return mercadoRepository.findById(id);
    }

    public Mercado save(Mercado mercado) {
        // Verificar que el país sea válido (Argentina o Uruguay)
        if (!"Argentina".equals(mercado.getPais().getNombre()) && !"Uruguay".equals(mercado.getPais().getNombre())) {
            throw new RuntimeException("El país debe ser Argentina o Uruguay.");
        }
        return mercadoRepository.save(mercado);
    }

    public void deleteById(Long id) {
        mercadoRepository.deleteById(id);
    }
}

