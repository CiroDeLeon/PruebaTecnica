package com.prueba.tecnica.nisum.pruebatecnica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.tecnica.nisum.pruebatecnica.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{
	@Query("select u from com.prueba.tecnica.nisum.pruebatecnica.entity.User u where u.email = ?1")	
	User findByEmail(String email);
	@Query("select u from com.prueba.tecnica.nisum.pruebatecnica.entity.User u where u.email = ?1 and u.password =?2")	
	User Login(String email,String password);
	@Query("select u.id from com.prueba.tecnica.nisum.pruebatecnica.entity.User u where u.id = ?1")	
	Long findByIdUser(Long id);
	@Query("select u.email from com.prueba.tecnica.nisum.pruebatecnica.entity.User u where u.id = ?1")
	String getMailById(Long id);
}
