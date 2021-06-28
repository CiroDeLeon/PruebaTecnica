package com.prueba.tecnica.nisum.pruebatecnica.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandler {   
   
   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ExceptionHandler(Exception.class) 
   public ResponseEntity<?> handleGlobalException(Exception e,WebRequest request){
	   Mensaje m=new Mensaje(e.getMessage());
	   return new ResponseEntity(m,HttpStatus.INTERNAL_SERVER_ERROR);
   }
}