package com.decrypto.api.decrypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.service.PaisService;

@RestController
@RequestMapping("/api/paises")
public class PaisController {
	
	@Autowired
	private PaisService paisService;
	
	
	@GetMapping
    public List<Pais> getAllPaises() {
        return paisService.getAllPaises();
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<Pais> getPaisById(@PathVariable Long id) {
	   return paisService.getPaisById(id)
	            .map(ResponseEntity::ok)
	            .orElse(ResponseEntity.notFound().build());
	  }

}
