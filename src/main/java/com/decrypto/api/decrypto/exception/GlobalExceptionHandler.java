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

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException exception, WebRequest request) {
		
		Map<String,String> mapErrors = new HashMap<>();
		
		exception.getBindingResult().getAllErrors().forEach((error) ->{
			String clave = ((FieldError)error).getField();
			String valor = error.getDefaultMessage();
			mapErrors.put(clave, valor);
			}
		);;
		
		
		String url = request.getDescription(false).replace("uri=", "");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        ApiResponse response = new ApiResponse(false, mapErrors.toString(), url, timestamp);
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    } 

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handlerResourceNotFoundException(ResourceNotFoundException exception, WebRequest request) {
        String url = request.getDescription(false).replace("uri=", "");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
        
        ApiResponse response = new ApiResponse(false, exception.getMessage(), url, timestamp);
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }
}

