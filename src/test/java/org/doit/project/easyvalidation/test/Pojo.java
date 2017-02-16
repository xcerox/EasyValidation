package org.doit.project.easyvalidation.test;

import org.doit.projectvalidation.annotations.Lenght;
import org.doit.projectvalidation.annotations.NotNull;
import org.doit.projectvalidation.annotations.Optional;

public class Pojo {
	
	private String nombre;
	private String Apellido;
	private Integer edad;
	
	public Pojo() {
		super();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@IsOptional
	@Lenght(min = 35)
	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

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
