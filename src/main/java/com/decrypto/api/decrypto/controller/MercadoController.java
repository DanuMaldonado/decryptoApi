package com.decrypto.api.decrypto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.service.MercadoService;

@RestController
@RequestMapping("/api/mercados")
public class MercadoController {
	
	 @Autowired
	 private MercadoService mercadoService;
	    
	    public MercadoController(MercadoService mercadoService) {
	        this.mercadoService = mercadoService;
	    }
	

	@GetMapping
	public ResponseEntity<List<Mercado>> getAllMercados() {
	    List<Mercado> mercados = mercadoService.findAll();
	    return ResponseEntity.ok(mercados);
	}

}
