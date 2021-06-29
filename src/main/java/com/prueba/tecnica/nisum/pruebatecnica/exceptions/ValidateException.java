package com.prueba.tecnica.nisum.pruebatecnica.exceptions;

@SuppressWarnings("serial")
public class ValidateException extends RuntimeException{
String message;

public ValidateException(String message) {
	super(message);
	this.message = message;
}

}
