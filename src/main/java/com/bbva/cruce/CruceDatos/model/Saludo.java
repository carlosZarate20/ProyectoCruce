package com.bbva.cruce.CruceDatos.model;


public class Saludo {

	public String mensaje;
	public String nombre;

	public Saludo(String mensaje, String nombre) {
		this.mensaje = mensaje;
		this.nombre = nombre;
	}
	
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Saludo [mensaje=" + mensaje + ", nombre=" + nombre + "]";
	}
	
	
	
}
