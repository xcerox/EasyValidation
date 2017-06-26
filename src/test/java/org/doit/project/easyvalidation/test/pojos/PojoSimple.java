package org.doit.project.easyvalidation.test.pojos;

import org.doit.easyvalidation.annotations.Identifinder;
import org.doit.easyvalidation.annotations.InNumbers;
import org.doit.easyvalidation.annotations.InStrings;
import org.doit.easyvalidation.annotations.IsOptional;
import org.doit.easyvalidation.annotations.Lenght;
import org.doit.easyvalidation.annotations.NotNull;
import org.doit.easyvalidation.annotations.Size;

public class PojoSimple {
	
	private String nombre;
	private String Apellido;
	private Integer edad;
	private String estado;
	
	public PojoSimple() {
		super();
	}

	@NotNull
	@Identifinder(PojoSimple.Identifinders.NOMBRE)
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@IsOptional
	@NotNull
	@Lenght(min = 5)
	@Identifinder(PojoSimple.Identifinders.APELLIDO)
	public String getApellido() {
		return Apellido;
	}

	public void setApellido(String apellido) {
		Apellido = apellido;
	}

	@NotNull
	@Size(min = 0)
	@InNumbers(values = {25, 30}) 
	public Integer getEdad() {
		return edad;
	}

	public void setEdad(Integer edad) {
		this.edad = edad;
	}

	@NotNull
	@InStrings(values = {PojoSimple.Estados.ACTIVO, PojoSimple.Estados.INACTIVO})
	@Identifinder(PojoSimple.Identifinders.ESTADO)
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "PojoSimple [nombre=" + nombre + ", Apellido=" + Apellido + ", edad=" + edad + ", estado=" + estado + "]";
	}
	
	public static class Identifinders {
		public static final String ESTADO = "Estado";
		public static final String NOMBRE = "Nombre";
		public static final String APELLIDO = "Apellido";
	}
	
	public static class Estados{
		public static final String ACTIVO = "AC";
		public static final String INACTIVO = "IN";
	}
}
