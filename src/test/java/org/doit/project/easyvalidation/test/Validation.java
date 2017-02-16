package org.doit.project.easyvalidation.test;

import org.doit.projectvalidation.core.ValidationCore;
import org.junit.Test;

public class Validation {

	@Test
	public void test() {
		
		Pojo pojoTest = new Pojo();
		pojoTest.setNombre("jairo");
		pojoTest.setApellido("Reyes");
		pojoTest.setEdad(25);
		
		ValidationCore validator = new ValidationCore();
		validator.validation(pojoTest, false);
	}
	


}
