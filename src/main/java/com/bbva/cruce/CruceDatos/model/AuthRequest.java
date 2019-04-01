package com.bbva.cruce.CruceDatos.model;

import java.io.Serializable;

public class AuthRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String base;
	private String group;
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getBase() {
		return base;
	}
	
	public void setBase(String base) {
		this.base = base;
	}
	
	public String getGroup() {
		return group;
	}
	
	public void setGroup(String group) {
		this.group = group;
	}
	
	
}