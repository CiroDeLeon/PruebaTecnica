package com.prueba.tecnica.nisum.pruebatecnica.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.prueba.tecnica.nisum.pruebatecnica.entity.Phone;



public interface PhoneRepository extends JpaRepository<Phone,Long>{
	@Query("select p from com.prueba.tecnica.nisum.pruebatecnica.entity.Phone p where p.user.id = ?1")
	List<Phone> getPhonesByIdUser(Long idUser);

}
