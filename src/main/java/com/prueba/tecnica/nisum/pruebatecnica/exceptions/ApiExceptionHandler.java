package com.prueba.tecnica.nisum.pruebatecnica.exceptions;



import java.io.IOException;

import javax.servlet.ServletException;

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
	   System.out.print(m.getMensaje()+" "+e.getClass().getSimpleName());
	   return new ResponseEntity(m,HttpStatus.INTERNAL_SERVER_ERROR);
   }
   
   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ExceptionHandler(ValidateException.class) 
   public ResponseEntity<?> handleValidateException(ValidateException e,WebRequest request){
	   Mensaje m=new Mensaje(e.getMessage());
	   System.out.print(m.getMensaje()+" "+e.getClass().getSimpleName());
	   return new ResponseEntity(m,HttpStatus.BAD_REQUEST);
   }
   
   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ExceptionHandler(NotFoundException.class) 
   public ResponseEntity<?> handleNotFoundException(NotFoundException e,WebRequest request){
	   Mensaje m=new Mensaje(e.getMessage());
	   System.out.print(m.getMensaje()+" "+e.getClass().getSimpleName());
	   return new ResponseEntity(m,HttpStatus.NOT_FOUND);
   }
   
   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ExceptionHandler(IOException.class) 
   public ResponseEntity<?> handleAccessDeniedException(IOException e,WebRequest request){
	   Mensaje m=new Mensaje(e.getMessage());
	   System.out.print(m.getMensaje()+" "+e.getClass().getSimpleName());
	   return new ResponseEntity(m,HttpStatus.FORBIDDEN);
   }
   @SuppressWarnings({ "rawtypes", "unchecked" })
   @ExceptionHandler(ServletException.class) 
   public ResponseEntity<?> handleServletException(ServletException e,WebRequest request){
	   Mensaje m=new Mensaje(e.getMessage());
	   System.out.print(m.getMensaje()+" "+e.getClass().getSimpleName());
	   return new ResponseEntity(m,HttpStatus.FORBIDDEN);
   }
   
   
}
