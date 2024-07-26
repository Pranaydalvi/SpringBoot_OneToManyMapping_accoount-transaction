package com.example.expo.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
@RestControllerAdvice
public class ExceptionHandller {

	@ExceptionHandler(AccountNotFound.class)
	public ResponseEntity<?> AccountNotFoundhandleException(HttpServletRequest request,AccountNotFound exception) {
		ExceptionData response = new ExceptionData();
		response.setGetUri(request.getRequestURI());
		response.setErrorMsg(exception.getMessage());
		return new ResponseEntity(response, HttpStatus.OK);
	}
	
	@ExceptionHandler(AccountNotActive.class)
	public ResponseEntity<?> AccountNotActivehandleException(HttpServletRequest request,AccountNotActive exception) {
		ExceptionData response = new ExceptionData();
		response.setGetUri(request.getRequestURI());
		response.setErrorMsg(exception.getMessage());
		return new ResponseEntity(response, HttpStatus.UNAUTHORIZED);
	}
	
	

}
