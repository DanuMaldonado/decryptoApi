package com.decrypto.api.decrypto;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.ComitenteRepository;
import com.decrypto.api.decrypto.repository.MercadoRepository;
import com.decrypto.api.decrypto.repository.PaisRepository;
import com.decrypto.api.decrypto.service.StatsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ComitenteRepository comitenteRepository;

    @Autowired
    private MercadoRepository mercadoRepository;

    @Autowired
    private PaisRepository paisRepository;
    
    private StatsService statsService;
    
    @Override
    public void run(String... args) throws Exception {
        // Limpiar la base de datos antes de agregar nuevos datos
        comitenteRepository.deleteAll();
        mercadoRepository.deleteAll();
        paisRepository.deleteAll();

        // Agregar países
        Pais argentina = new Pais(NombrePais.ARGENTINA);
        Pais uruguay = new Pais(NombrePais.URUGUAY);
        paisRepository.saveAll(Arrays.asList(argentina, uruguay));

        // Agregar mercados
        Mercado mercadoMAE = new Mercado();
        mercadoMAE.setCodigo("MAE");
        mercadoMAE.setDescripcion("Mercado Abierto Electrónico");
        mercadoMAE.setPais(argentina);
        mercadoRepository.save(mercadoMAE);

        Mercado mercadoROFEX = new Mercado();
        mercadoROFEX.setCodigo("ROFEX");
        mercadoROFEX.setDescripcion("Mercado a Término de Rosario");
        mercadoROFEX.setPais(argentina);
        mercadoRepository.save(mercadoROFEX);
        
        Mercado mercadoUFEX = new Mercado();
        mercadoUFEX.setCodigo("UFEX");
        mercadoUFEX.setDescripcion("Mercado de Futuros y Opciones");
        mercadoUFEX.setPais(uruguay);
        mercadoRepository.save(mercadoUFEX);

        // Agregar comitentes
        Comitente comitente1 = new Comitente();
        comitente1.setDescripcion("Comitente MAE1");
        comitente1.getMercados().add(mercadoMAE);
        comitenteRepository.save(comitente1);
        
        Comitente comitente2 = new Comitente();
        comitente2.setDescripcion("Comitente ROFEX1");
        comitente2.getMercados().add(mercadoROFEX);
        comitenteRepository.save(comitente2);
        
        Comitente comitente3 = new Comitente();
        comitente3.setDescripcion("Comitente UFEX1");
        comitente3.getMercados().add(mercadoUFEX);
        comitenteRepository.save(comitente3);
        
        Comitente comitente4 = new Comitente();
        comitente4.setDescripcion("Comitente UFEX2");
        comitente4.getMercados().add(mercadoUFEX);
        comitenteRepository.save(comitente4);
        
    }
}
