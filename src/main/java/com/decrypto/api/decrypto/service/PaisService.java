package com.decrypto.api.decrypto.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.PaisRepository;

@Service
public class PaisService {
	
	@Autowired
	private PaisRepository  paisRepository;
	
	 public Optional<Pais> getPaisById(Long id) {
	        return paisRepository.findById(id);
	    }

}
