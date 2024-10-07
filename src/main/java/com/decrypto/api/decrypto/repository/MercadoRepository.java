package com.decrypto.api.decrypto.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.Pais;

public interface MercadoRepository extends JpaRepository<Mercado, Long> { 
	
	boolean existsByCodigo(String codigo);
	
	Mercado findByCodigo(String codigo);

	Mercado findByCodigoAndPais(String codigo, Pais pais);

	
}
