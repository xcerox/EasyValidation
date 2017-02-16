package org.doit.project.easyvalidation.test;

import org.doit.project.easyvalidation.annotations.IsOptional;
import org.doit.project.easyvalidation.annotations.Lenght;
import org.doit.project.easyvalidation.annotations.NotNull;

public class Pojo {
	
	private String nombre;
	private String Apellido;
	private Integer edad;
	
	public Pojo() {
		super();
	}

	@NotNull
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@IsOptional
	@Lenght(min = 5)
	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	@NotNull
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@Override
	public String toString() {
		return "Pojo [nombre=" + nombre + ", Apellido=" + Apellido + ", edad=" + edad + "]";
	}
}
