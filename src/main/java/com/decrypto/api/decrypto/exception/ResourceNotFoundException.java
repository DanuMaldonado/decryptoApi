package com.decrypto.api.decrypto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Excepción personalizada que se lanza cuando un recurso no se encuentra.
 * Se utiliza para indicar que el recurso solicitado no existe en el sistema.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String resourceName; // Nombre del recurso que no se encontró
    private String fieldName;     // Nombre del campo que se utilizó para buscar el recurso	private Object fieldValue;
    private Object fieldValue;    // Valor del campo que se utilizó para buscar el recurso
	
    /**
     * Constructor que inicializa la excepción con un mensaje que indica que un recurso específico no fue encontrado.
     * 
     * @param resourceName El nombre del recurso que no fue encontrado.
     * @param fieldName El nombre del campo utilizado para la búsqueda.
     * @param fieldValue El valor del campo utilizado para la búsqueda.
     */
	public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
		super(String.format("%s No fue encontrado con: %s=%s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	/**
     * Constructor que inicializa la excepción con un mensaje que indica que no hay registros de un recurso específico.
     * 
     * @param resourceName El nombre del recurso para el cual no hay registros.
     */
	public ResourceNotFoundException(String resourceName) {
		super(String.format("No hay registros de %s", resourceName));
		this.resourceName = resourceName;
	}
	
    // Getters para acceder a los campos adicionales si es necesario
    public String getResourceName() {
        return resourceName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }
}
