package org.doit.project.easyvalidation.test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.doit.project.easyvalidation.core.ValidationCore;
import org.doit.project.easyvalidation.test.pojos.PojoSimple;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidationSimplePojo {

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

		}
	}

	@Test
	public void validationWithOptional_PojoComplete_FailsEmpty() {

		PojoSimple pojoTest = new PojoSimple();
		pojoTest.setNombre("Frank");
		pojoTest.setApellido("Perez");
		pojoTest.setEdad(25);
		pojoTest.setEstado("IN");

		boolean validateOptional = true;

		if (validator.validation(pojoTest, validateOptional)) {

			Optional<Map<String, List<String>>> fails = validator.getFails();
			Assert.assertTrue(fails.isPresent());
			Assert.assertTrue(fails.get().isEmpty());

		}

	}

	@Test
	public void validationWithOptional_PojoEmpty_Fails() {

		PojoSimple pojoTest = new PojoSimple();

		boolean validateOptional = true;

		if (validator.validation(pojoTest, validateOptional)) {
			Optional<Map<String, List<String>>> fails = validator.getFails();

			if (fails.isPresent()) {
				Assert.assertTrue(fails.get().size() > 0);
			}
		}
	}
	
	
	//
	// @Test
	// public void testNotNullOptional_ObjectEmpty_True() {
	// Pojo pojoTest = new Pojo();
	//
	// boolean validateOptional = true;
	//
	// if (!validator.validation(pojoTest, validateOptional)){
	// Optional<List<String>> fauls = validator.getFauls();
	//
	// if (fauls.isPresent()) {
	//
	// for (String fault : fauls.get()) {
	// System.out.println(">> fault: " + fault);
	//
	// }
	//
	// Assert.assertTrue(fauls.get().size() > 0);
	// }
	// }
	//
	// Assert.fail();
	// }
	//
	//
	// private void printFault(Map<String, List<String>> methods) {
	// for (String method : methods.keySet()) {
	// System.out.println(">Method: " + method);
	//
	// for (String fault : methods.get(method)) {
	// System.out.println(">> fault: " + fault);
	//
	// }
	// }
	// }
}
