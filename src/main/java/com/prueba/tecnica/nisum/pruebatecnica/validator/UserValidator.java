package com.prueba.tecnica.nisum.pruebatecnica.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.prueba.tecnica.nisum.pruebatecnica.entity.User;

public class UserValidator {
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);
	
	
   public static boolean isValidMail(String email) {	                                                                           
	   Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
       return matcher.matches();
   }
   
   
   public static boolean isValidPassword(String password) {
	   Pattern lowercase = Pattern.compile("[a-z]");
	   Pattern uppercase = Pattern.compile("[A-Z]");
	   Pattern digit = Pattern.compile("[0-9]");
	   
	   Matcher hasLowerCase = lowercase.matcher(password);
	   Matcher hasUpperCase = uppercase.matcher(password);
	   Matcher hasDigit = digit.matcher(password);
	   
	   
	   
	   return hasLowerCase.find() && hasUpperCase.find() && hasDigit.results().count()>=2;
   }
   
   public static String getValidationMessage(User u) {
	   String mensaje="";
	   if(UserValidator.isValidPassword(u.getPassword())==false) {
		   mensaje+="El password debe contener por lo menos un caracter lowercase,un caracter uppercase y dos digitos numericos";
		   return mensaje;
	   }
	   if(UserValidator.isValidMail(u.getEmail())==false) {
		   mensaje+="El correo es invalido";
		   return mensaje;
	   }
	   
	   
	   return "";
   }
   public static boolean isValid(User u) {
	   
	   if(UserValidator.isValidPassword(u.getPassword())==false) {		   
		   return false;
	   }
	   if(UserValidator.isValidMail(u.getEmail())==false) {
		   
		   return false;
	   }
	   
	   
	   return true;
   }
   public static void main(String[] args) {
	   boolean res=UserValidator.isValidPassword("10C78T4054ciro");
	   System.out.print(res);
   }
   
}
