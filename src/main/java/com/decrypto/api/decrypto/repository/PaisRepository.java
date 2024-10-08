package com.decrypto.api.decrypto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, Long> { 
	
	/**
    * Busca un Pais por su nombre.
    *
    * @param nombre el nombre del Pais
    * @return un Optional que contiene el Pais encontrado, o vacío si no se encuentra
    */
	Pais findByNombre(NombrePais nombre);
}

