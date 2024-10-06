package com.decrypto.api.decrypto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.NombrePais;

public interface ComitenteRepository extends JpaRepository<Comitente, Long> {

	boolean existsByDescripcion(String descripcion);
	
    Optional<Comitente> findByDescripcion(String descripcion);
    
    @Query("SELECT c FROM Comitente c JOIN c.mercados m WHERE m.pais.nombre = :pais")
    List<Comitente> findByPais(@Param("pais") NombrePais pais);
    
    
}
