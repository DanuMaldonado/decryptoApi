package com.decrypto.api.decrypto.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.Pais;

public interface MercadoRepository extends JpaRepository<Mercado, Long> { 
	
	 /**
     * Busca un Mercado por su código.
     *
     * @param codigo el código del Mercado
     * @return el Mercado encontrado
     */
	Mercado findByCodigo(String codigo);

	   /**
     * Busca un Mercado por su código y su país asociado.
     *
     * @param codigo el código del Mercado
     * @param pais el país asociado al Mercado
     * @return el Mercado encontrado
     */
	Mercado findByCodigoAndPais(String codigo, Pais pais);

	
}
