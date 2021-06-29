package com.prueba.tecnica.nisum.pruebatecnica.entity;

import java.io.Serializable;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name = "phone")
public class Phone implements Serializable{	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	Long id;
	
	
	
	@ManyToOne
    @JoinColumn(name="user_id",nullable=false)
	User user;	
	String number;	
	String citycode;
	String contrycode;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id=id;
	}
	
	public void setUser(User user) {		
		this.user = user;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCitycode() {
		return citycode;
	}
	public void setCitycode(String cityCode) {
		this.citycode = cityCode;
	}
	public String getContrycode() {
		return contrycode;
	}
	public void setContrycode(String countryCode) {
		this.contrycode = countryCode;
	}
}
