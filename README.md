# EasyValidation
It's a small library that can help us with pojo's validation through the annotations

made using 
Eclipse Luna 4.4.2, Java 8, Maven, JUnit 4, Reflections, slf4j 1.6.4, logback 1.0.1, json 20090211.

**Test case **
```java
	private static ValidationCore validator;

	@BeforeClass
	public static void setUpBeforeClass() {
		validator = new ValidationCore();
	}

	@AfterClass
	public static void tearDownAfterClass() {
		validator = null;
	}

	@Test
	public void validationWithoutOptional_PojoWithoutApellido_FailsEmpty() {

		PojoSimple pojoTest = new PojoSimple();
		pojoTest.setNombre("Frank");
		pojoTest.setEdad(25);
		pojoTest.setEstado("AC");

		boolean validateOptional = false;

		if (validator.validation(pojoTest, validateOptional)) {

			Optional<Map<String, List<String>>> fails = validator.getFails();
			Assert.assertTrue(fails.isPresent());
			Assert.assertTrue(fails.get().isEmpty());
		} else {
			Assert.fail("Validation failed");
		}
	}
```


**Pojo example**
```java
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
```
**Example annotation**
```java
		@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = InStringsValidator.class, id= InStrings.ID)
public @interface InStrings {
	
	public static final String ID = "DOIT@IN_STRINGS";
	public static final String MULTIPLE_VALUES = "values";
	
	@Property(MULTIPLE_VALUES)
	public String[] values() default {Empty.STRING};
}
```

**Class InStringsValidator**
```java
		import org.doit.easyvalidation.annotations.InStrings;
import org.doit.easyvalidation.consts.LoggerMessage;
import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.interfaces.Validator;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InStringsValidator implements Validator<String>{

	private static final Logger LOGGER = LoggerFactory.getLogger(InStringsValidator.class);
	
	public InStringsValidator() {
		super();
	}

	@Override
	public boolean isValid(String value, JSONObject propierties) throws InternalException, JSONException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "value", value);
		LOGGER.debug(LoggerMessage.PARAMETER, "propierties", propierties);
		boolean isValid = false;
		
		String[] options = (String[]) propierties.get(InStrings.MULTIPLE_VALUES);
		
		for (String option : options) {
			if (option.equals(value)) {
				isValid = true;
				break;
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, isValid);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return isValid;
	}
}
```
