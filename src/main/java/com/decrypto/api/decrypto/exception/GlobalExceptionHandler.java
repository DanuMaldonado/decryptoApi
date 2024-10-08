package com.decrypto.api.decrypto.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.decrypto.api.decrypto.response.ApiResponse;

/**
 * Manejador global de excepciones para la API.
 * Este controlador se encarga de capturar y manejar excepciones específicas que puedan ocurrir durante la ejecución de la API.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	
	/**
     * Maneja las excepciones {@link MethodArgumentNotValidException} que ocurren cuando los argumentos de un método no son válidos.
     * 
     * @param exception La excepción lanzada cuando hay argumentos inválidos.
     * @param request El objeto WebRequest que contiene detalles de la solicitud.
     * @return Una respuesta con un mensaje de error y detalles de los campos inválidos.
     */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
		
		Map<String,String> mapErrors = new HashMap<>();
		
		// Recorrer los errores de validación y almacenarlos en un mapa con clave: campo, valor: mensaje de error.
		exception.getBindingResult().getAllErrors().forEach((error) ->{
			String clave = ((FieldError)error).getField();
			String valor = error.getDefaultMessage();
			mapErrors.put(clave, valor);
			}
		);;
		
		// Obtener detalles adicionales de la solicitud
		String url = request.getDescription(false).replace("uri=", "");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        // Crear una respuesta estandarizada para la API
        ApiResponse response = new ApiResponse(false, mapErrors.toString(), url, timestamp);
        
        // Retornar un ResponseEntity con el código 400 (Bad Request) y el contenido en formato JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    } 

	 /**
     * Maneja las excepciones {@link ResourceNotFoundException} que ocurren cuando no se encuentra un recurso solicitado.
     * 
     * @param exception La excepción lanzada cuando no se encuentra el recurso.
     * @param request El objeto WebRequest que contiene detalles de la solicitud.
     * @return Una respuesta con un mensaje de error y el estado 404 (Not Found).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        String url = request.getDescription(false).replace("uri=", "");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        // Crear una respuesta estandarizada para cuando un recurso no se encuentra
        ApiResponse response = new ApiResponse(false, exception.getMessage(), url, timestamp);
        
        // Retornar un ResponseEntity con el código 404 (Not Found) y el contenido en formato JSON
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

