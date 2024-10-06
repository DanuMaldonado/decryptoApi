package com.decrypto.api.decrypto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.decrypto.api.decrypto.model.Mercado;

public interface MercadoRepository extends JpaRepository<Mercado, Long> { 
	
	boolean existsByCodigo(String codigo);
	
	Optional<Mercado> findByCodigo(String codigo);

}
