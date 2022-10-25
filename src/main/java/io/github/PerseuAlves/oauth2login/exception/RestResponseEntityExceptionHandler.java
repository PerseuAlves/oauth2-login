package io.github.PerseuAlves.oauth2login.exception;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.github.PerseuAlves.oauth2login.exception.model.StandardError;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private WebRequest request;
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex) {
		
		String error = "resource not found";
		HttpStatus status = HttpStatus.NOT_FOUND;
		StandardError err = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(status).body(err);
	}
	
	@ExceptionHandler(ResourceAlreadyPresentException.class)
	public ResponseEntity<StandardError> resourceAlreadyPresent(ResourceAlreadyPresentException ex) {
		
		String error = "resource already present";
		HttpStatus status = HttpStatus.CONFLICT;
		StandardError err = new StandardError(Instant.now(), status.value(), error, ex.getMessage(), request.getDescription(false));
		
		return ResponseEntity.status(status).body(err);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<StandardError> illegalArgument(IllegalArgumentException ex) {

		String error = "invalid argument";
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		StandardError err = new StandardError(Instant.now(), status.value(), error, ex.getMessage(),
				request.getDescription(false));

		return ResponseEntity.status(status).body(err);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getField())
				.collect(Collectors.toList());
		
		Map<String, Object> body = new LinkedHashMap<>();
		
		body.put("timestamp", LocalDate.now());
		body.put("status", status.value());
		body.put("errors", errors);
		body.put("message", "invalid params");
		body.put("path", request.getDescription(false));
		
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
}
