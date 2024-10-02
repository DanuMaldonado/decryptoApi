package com.decrypto.api.decrypto;

import com.decrypto.api.decrypto.model.Comitente;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.ComitenteRepository;
import com.decrypto.api.decrypto.repository.MercadoRepository;
import com.decrypto.api.decrypto.repository.PaisRepository;
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
        mercadoMAE.setPorcentaje(15.20);
        mercadoRepository.save(mercadoMAE);

        Mercado mercadoROFEX = new Mercado();
        mercadoROFEX.setCodigo("ROFEX");
        mercadoROFEX.setDescripcion("Mercado a Término de Rosario");
        mercadoROFEX.setPais(argentina);
        mercadoROFEX.setPorcentaje(40.75);
        mercadoRepository.save(mercadoROFEX);
        
        Mercado mercadoUFEX = new Mercado();
        mercadoUFEX.setCodigo("UFEX");
        mercadoUFEX.setDescripcion("Mercado de Futuros y Opciones");
        mercadoUFEX.setPais(uruguay);
        mercadoUFEX.setPorcentaje(44.05);
        mercadoRepository.save(mercadoUFEX);

        // Agregar comitentes
        Comitente comitente1 = new Comitente();
        comitente1.setDescripcion("Comitente 1");
        comitente1.getMercados().add(mercadoMAE);
        comitenteRepository.save(comitente1);

        Comitente comitente2 = new Comitente();
        comitente2.setDescripcion("Comitente 2");
        comitente2.getMercados().add(mercadoROFEX);
        comitenteRepository.save(comitente2);
        
        Comitente comitente3 = new Comitente();
        comitente3.setDescripcion("Comitente 3");
        comitente3.getMercados().add(mercadoUFEX);
        comitenteRepository.save(comitente3);
    }
}
