package com.decrypto.api.decrypto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.decrypto.api.decrypto.dto.MercadoDTO;
import com.decrypto.api.decrypto.model.Mercado;
import com.decrypto.api.decrypto.model.NombrePais;
import com.decrypto.api.decrypto.model.Pais;
import com.decrypto.api.decrypto.repository.MercadoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse; 
import io.swagger.v3.oas.annotations.responses.ApiResponses; 

@Service
public class MercadoService {
	
    @Autowired
    private MercadoRepository mercadoRepository;
    
    @Autowired
    private PaisService paisService;
    
 
    @Operation(summary = "Obtener todos los Mercados", description = "Retorna una lista de todos los Mercados.")
    public List<Mercado> findAll() {
        return mercadoRepository.findAll();
    }

    @Operation(summary = "Obtener un Mercado por ID", description = "Retorna un Mercado basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Mercado encontrado"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado")
    })
    public Optional<Mercado> findById(@Parameter(description = "ID del Mercado a buscar") Long id) {
        return mercadoRepository.findById(id);
    }
    
    @Operation(summary = "Encontrar Mercados por lista de IDs", description = "Retorna una lista de Mercados basados en los IDs proporcionados.")
    public List<Mercado> findByIds(@Parameter(description = "Lista de IDs de Mercados") List<Long> ids) {
        return ids.stream()
                  .map(mercadoRepository::findById)
                  .filter(Optional::isPresent)
                  .map(Optional::get)
                  .collect(Collectors.toList());
    }

    @Operation(summary = "Guardar un nuevo Mercado", description = "Crea y guarda un nuevo Mercado a partir de un DTO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mercado creado con éxito"),
            @ApiResponse(responseCode = "404", description = "País no encontrado"),
            @ApiResponse(responseCode = "400", description = "País inválido")
        })
    public MercadoDTO save(@Parameter(description = "Datos del Mercado a crear") MercadoDTO mercadoDTO) {
            // Buscar el país por ID desde el servicio PaisService
        Optional<Pais> paisOptional = paisService.getPaisById(mercadoDTO.getPaisId());
        
        if (!paisOptional.isPresent()) {
            throw new RuntimeException("País no encontrado.");
        }

        Pais pais = paisOptional.get();

        // Verificar que el país sea válido (Argentina o Uruguay)
        if (NombrePais.ARGENTINA.equals(pais.getNombre()) && NombrePais.URUGUAY.equals(pais.getNombre())) {
            throw new RuntimeException("El país debe ser Argentina o Uruguay.");
        }

        // Crear una nueva instancia de Mercado a partir del DTO
        Mercado mercado = new Mercado();
        mercado.setCodigo(mercadoDTO.getCodigo());
        mercado.setDescripcion(mercadoDTO.getDescripcion());
        mercado.setPais(pais); // Asignar el país encontrado
        mercado.setPorcentaje(mercadoDTO.getPorcentaje());

        // Guardar el mercado
        Mercado savedMercado = mercadoRepository.save(mercado);

        // Convertir el Mercado guardado a un MercadoDTO
        MercadoDTO savedMercadoDTO = new MercadoDTO();
        savedMercadoDTO.setId(savedMercado.getId());
        savedMercadoDTO.setCodigo(savedMercado.getCodigo());
        savedMercadoDTO.setDescripcion(savedMercado.getDescripcion());
        savedMercadoDTO.setPaisId(savedMercado.getPais().getId()); // Obtener el ID del país
        savedMercadoDTO.setPorcentaje(savedMercado.getPorcentaje());

        return savedMercadoDTO;

    }
    @Operation(summary = "Eliminar un Mercado por ID", description = "Elimina un Mercado basado en su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Mercado eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "Mercado no encontrado")
    })
    public void deleteById(@Parameter(description = "ID del Mercado a eliminar") Long id) {
        mercadoRepository.deleteById(id);
    }
}

