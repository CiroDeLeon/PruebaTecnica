package com.prueba.tecnica.nisum.PruebaTecnica;



import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prueba.tecnica.nisum.pruebatecnica.controller.UserRestService;
import com.prueba.tecnica.nisum.pruebatecnica.entity.Phone;
import com.prueba.tecnica.nisum.pruebatecnica.entity.User;



@RunWith(SpringRunner.class)
@SpringBootTest
class PruebaTecnicaApplicationTests {
	@Autowired
	UserRestService service;
	@Test
	void contextLoads() {	
	
		User user=new User();		
		user.setName("ciro");
		user.setEmail("elalumnopc@hotmail.com");
		user.setPassword("J12dhdhd");
		List<Phone> lista=new ArrayList<Phone>();
		
		Phone pa=new Phone();
		pa.setNumber("3846446");
		pa.setCitycode("233");
		pa.setContrycode("12");
		
		Phone pb=new Phone();
		pb.setNumber("3846446");
		pb.setCitycode("233");
		pb.setContrycode("12");
		
		lista.add(pa);
		lista.add(pb);
		
		user.setPhones(lista);
		
		User response=service.createUser(user);
		System.out.println(response.getToken());
		boolean succesTestFlag=response!=null;
		Assert.assertTrue(succesTestFlag);
		
		
		
		
		
	}

}
