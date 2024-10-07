package com.decrypto.api.decrypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> { 
	
	Pais findByNombre(NombrePais nombre);
}

