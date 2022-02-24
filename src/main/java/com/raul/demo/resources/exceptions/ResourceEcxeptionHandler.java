package com.raul.demo.resources.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.raul.demo.services.exceptions.DataIntegratyViolationException;
import com.raul.demo.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceEcxeptionHandler {

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException ex, HttpServletRequest request){
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error = 
				new StandardError(LocalDateTime.now(), status.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(status).body(error);
	}
	
	@ExceptionHandler(DataIntegratyViolationException.class)
    public ResponseEntity<StandardError>dataIntegrityViolationException(DataIntegratyViolationException ex, HttpServletRequest request) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError error =
                new StandardError(LocalDateTime.now(), status.value(), ex.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }
}
