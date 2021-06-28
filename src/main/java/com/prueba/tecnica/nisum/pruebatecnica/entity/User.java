package com.prueba.tecnica.nisum.pruebatecnica.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
@Table(name = "user")
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	String name;
	String email;
	String password;
	@OneToMany(mappedBy="user")
	List<Phone> phones;	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date created;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date modified;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
	Date last_login;
	String token;
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getModified() {
		return modified;
	}
	public void setModified(Date modified) {
		this.modified = modified;
	}
	public Date getLast_login() {
		return last_login;
	}
	public void setLast_login(Date last_login) {
		this.last_login = last_login;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Phone> getPhones() {
		return phones;
	}
	public void setPhones(List<Phone> phones) {
		this.phones = phones;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
