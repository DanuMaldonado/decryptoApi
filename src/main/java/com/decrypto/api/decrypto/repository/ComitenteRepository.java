package com.decrypto.api.decrypto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.NombrePais;

public interface ComitenteRepository extends JpaRepository<Comitente, Long> {
	
	/**
     * Busca un Comitente por su descripción.
     *
     * @param descripcion la descripción del Comitente
     * @return un Optional que contiene el Comitente si se encuentra, vacío en caso contrario
     */
    Optional<Comitente> findByDescripcion(String descripcion);
    
    /**
     * Encuentra todos los Comitentes relacionados con un país específico.
     *
     * @param pais el nombre del país
     * @return una lista de Comitentes que pertenecen al país
     */
    @Query("SELECT c FROM Comitente c JOIN c.mercados m WHERE m.pais.nombre = :pais")
    List<Comitente> findByPais(@Param("pais") NombrePais pais);
    
    
}
