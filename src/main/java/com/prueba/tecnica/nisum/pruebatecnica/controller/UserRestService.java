package com.prueba.tecnica.nisum.pruebatecnica.controller;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.tecnica.nisum.pruebatecnica.entity.Phone;
import com.prueba.tecnica.nisum.pruebatecnica.entity.User;
import com.prueba.tecnica.nisum.pruebatecnica.exceptions.Mensaje;
import com.prueba.tecnica.nisum.pruebatecnica.exceptions.NotFoundException;
import com.prueba.tecnica.nisum.pruebatecnica.exceptions.ValidateException;
import com.prueba.tecnica.nisum.pruebatecnica.repository.PhoneRepository;
import com.prueba.tecnica.nisum.pruebatecnica.repository.UserRepository;
import com.prueba.tecnica.nisum.pruebatecnica.validator.UserValidator;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;





@RestController
public class UserRestService {
	
	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PhoneRepository phoneRepository;
	
	@PostMapping(value="User/Login",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)   
	public User Login(@RequestBody User user) throws Exception{
		User u=userRepository.Login(user.getEmail(),user.getPassword());
		if(u!=null) {
			Date currentDate=Calendar.getInstance().getTime();           	
			u.setToken(getTokenValue(u));
			u.setLast_login(currentDate);
			userRepository.saveAndFlush(u);
			return u;
		}else {
			throw new Exception("Login Invalido");
		}		
	}
	
	@PostMapping(value="/User",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)   
	 public User createUser(@RequestBody User user) throws ValidateException{
		if(user.getPhones()!=null) {
	    if(idIsNull(user)) {
	       if(allIdsAreNull(user.getPhones())) {	
              if(UserValidator.isValid(user)) {	   
	             if(emailWasNotCreated(user)) {		  
	            	Date currentDate=Calendar.getInstance().getTime();
		            user.setCreated(currentDate);
		            user.setModified(currentDate);
	            	user.setLast_login(currentDate);
		            return saveUser(user);
	             }else {
	        	    throw new ValidateException("Este email ya fue registrado en el sistema");
	             }
              }else {
   	             throw new ValidateException(UserValidator.getValidationMessage(user));
              }
	       }else {
	    	   throw new ValidateException("Para crear usuarios el id de cada Phone debe ser null");
	       }
	    }else{
	    	throw new ValidateException("Para crear usuarios el id del User debe ser null");
	    }
		}else {
			throw new ValidateException("phones debe ser diferente de null ");
		}
    }   
	@PutMapping(value="/User",produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)   
	 public User updateUser(@RequestBody User u) throws ValidateException,NotFoundException{
		if(!idIsNull(u)) {  
		      Long idUser=userRepository.findByIdUser(u.getId());
		      List<Phone> userPhones=phoneRepository.getPhonesByIdUser(idUser);
		      if(idUser==u.getId()) {		    	  
		         if(!existPhonesWithIdInvalid(userPhones,u.getPhones())) {
		            if(UserValidator.isValid(u)) {
		               String userEmail=userRepository.getMailById(idUser);	
		    	       if(userEmail.equals(u.getEmail())) {
		    		      this.DeletePhones(userPhones,u.getPhones());
		    		      Date currentDate=Calendar.getInstance().getTime();			            	
			              u.setModified(currentDate);
		    	          return saveUser(u);
		    	       }else {
		    		      if(emailWasNotCreated(u)) {
		    			     this.DeletePhones(userPhones,u.getPhones());
		    			     Date currentDate=Calendar.getInstance().getTime();			            	
				             u.setModified(currentDate);
		    			     return saveUser(u);	    			  
		    		      }else {
		    			     throw new ValidateException("Este email ya fue registrado en el sistema por lo cual no puede asignarlo al usuario");
		    		      }	  
		    	       }
		            }else {
		    	       throw new ValidateException(UserValidator.getValidationMessage(u)); 
		            }
		         }else{
		    	    throw new ValidateException(existPhonesWithIdInvalidMessage(userPhones,u.getPhones()));
		         }
		      }else {
		    	  throw new NotFoundException("El usuario con id "+u.getId()+" no existe en la base de datos por lo cual no puede ser modificado");
		      }
		}else {
			throw new ValidateException("No es posible modificar el usuario debido a que el id es null ");
		}
  }   
   
	private void DeletePhones(List<Phone> database,List<Phone> ram) {
		Iterator <Phone> it =this.getPhonesForDelete(database, ram).iterator();
		while(it.hasNext()) {
			Phone obj=it.next();
			phoneRepository.delete(obj);
		}
	}
	private boolean existPhonesWithIdInvalid(List<Phone> database,List<Phone> ram) {
		if(this.getPhonesThatNotExistInDB(database, ram).size()>0) {
			return true;
		}
		return false;
	}
	private String existPhonesWithIdInvalidMessage(List<Phone> database,List<Phone> ram) {
		String message="El id de los siguientes Phones [ ";
		Iterator<Phone>it=this.getPhonesThatNotExistInDB(database, ram).iterator();
		while(it.hasNext()) {
			Phone obj=it.next();
			message+=obj.getId()+" ";
		}
		message+="] No existen en la base de datos para ser modificados";
		return message;
	}
	private List<Phone> getPhonesThatNotExistInDB(List<Phone> database,List<Phone> ram){
		List<Phone> lista=new ArrayList<Phone>();
		for(int i=0;i<ram.size();i++) {
			Phone phoneRam=ram.get(i);
			if(phoneRam.getId()!=null) {
				boolean existInDB=false;
				for(int j=0;j<database.size();j++) {
					Phone phoneDB=database.get(j);
					if(phoneDB.getId().longValue()==phoneRam.getId().longValue()) {
						existInDB=true;
					}
				}
				if(existInDB==false) {
					lista.add(phoneRam);
				}
			}
		}
		return lista;
	}
	private List<Phone> getPhonesForDelete(List<Phone> database,List<Phone> ram){
		List<Phone> toDelete=new ArrayList<Phone>();
		for(int i=0;i<database.size();i++) {
		   Phone db=database.get(i);
		   boolean mustBeDelete=true;
		   for(int j=0;j<ram.size();j++) {
		      if(ram.get(j).getId()!=null) {
		         if(ram.get(j).getId().longValue()==db.getId().longValue()) {
		        	 mustBeDelete=false;
		         }
		      }
		   }
		   if(mustBeDelete) {
			   toDelete.add(db);
		   }
		}
		return toDelete;
	}
	private User saveUser(User u) {
	    u.setToken(getTokenValue(u));
	    userRepository.saveAndFlush(u);	    	    
	    Iterator<Phone> it=u.getPhones().iterator();
	    while(it.hasNext()) {
		   Phone obj=it.next();		   
		   obj.setUser(u);		   
		   phoneRepository.saveAndFlush(obj);		   
	    }	    
	    
       return u;
   }
	private boolean allIdsAreNull(List<Phone> phones) {		
		Iterator<Phone> it=phones.iterator();
		while(it.hasNext()) {
			Phone obj=it.next();
			if(obj.getId()!=null) {
				return false;
			}
		}
		return true;
	}
	private boolean idIsNull(User u) {
		   return u.getId()==null;
	}
   private boolean emailWasNotCreated(User u) {
	   return userRepository.findByEmail(u.getEmail())==null;
   }
   private String getTokenValue(User u) {
	    
	    
	    
	    String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("ROLE_USER");
		
		String token = Jwts
				.builder()
				.setId("softtekJWT")
				.setSubject(u.getEmail())
				.claim("authorities",
						grantedAuthorities.stream()
								.map(GrantedAuthority::getAuthority)
								.collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 900000))
				.signWith(SignatureAlgorithm.HS512,
						secretKey.getBytes()).compact();

		return "Bearer " + token;
   }
   
   @GetMapping(value="/User",produces=MediaType.APPLICATION_JSON_VALUE)   
   public List<User> getUsers() throws NotFoundException{
	   List<User> lista=new ArrayList<User>();
	   lista=userRepository.findAll();	   
	   if(lista.size()>0) {
	      return lista;
	   }else {
		   throw new NotFoundException("No ha sido ingresado ningun usuario"); 
	   }
   }
   @GetMapping(value="/User/{id}",produces=MediaType.APPLICATION_JSON_VALUE)   
   public User getUser(@PathVariable Long id) throws NotFoundException{
	   try {
	      User user=userRepository.findById(id).get();
	      return user;
	   }catch(Exception e) {
		   throw new NotFoundException("El usuario con id "+id+" no existe en la base de datos");
	   }	   
   }
   @DeleteMapping(value="/User")
   public void mensajeInformativo() throws ValidateException {
	  throw new ValidateException("Para eliminar debe anexar el idUser en la url endpoint/contextPath/User/{idUser}");
   }
   
   @DeleteMapping(value="/User/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
   public Mensaje removeUser(@PathVariable Long id) throws NotFoundException{	   
	   try {
		  User user=userRepository.findById(id).get(); 
		  Iterator<Phone> it=user.getPhones().iterator();
		  while(it.hasNext()) {
			  Phone obj=it.next();
			  phoneRepository.delete(obj);
		  }
	      userRepository.delete(user);
	      Mensaje m=new Mensaje("Usuario eliminado exitosamente id="+user.getId()+" email="+user.getEmail());
	      return m;
	   }catch(Exception e) {
	      throw new NotFoundException("El usuario con id "+id+" No Existe en nuestra base de datos por lo cual no es posible eliminarlo");
	   }
   }
   
   
   
   /*
   @GetMapping(value="/Phone",produces=MediaType.APPLICATION_JSON_VALUE)   
   public List<Phone> getPhones() {
	   List<Phone> lista=new ArrayList<Phone>();
	   lista=phoneRepository.findAll();	   
	   return lista;
   }
   */
   
}
