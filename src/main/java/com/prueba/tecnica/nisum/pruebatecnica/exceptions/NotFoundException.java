package com.prueba.tecnica.nisum.pruebatecnica.exceptions;

@SuppressWarnings("serial")
public class NotFoundException extends RuntimeException{
   String message;

public NotFoundException(String message) {
	super(message);
	this.message = message;
}
}
