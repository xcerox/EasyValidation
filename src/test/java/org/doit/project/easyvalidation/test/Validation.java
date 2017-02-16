package org.doit.project.easyvalidation.test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.doit.project.easyvalidation.core.ValidationCore;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


public class Validation {

	private static ValidationCore validator;
	
	@BeforeClass
	public static void setUp(){
		validator = new ValidationCore();
	}
	
	@Test
	public void testNotNull_ObjectFull_True() {
		
		Pojo pojoTest = new Pojo();
		pojoTest.setNombre("jairo");
		pojoTest.setApellido("Reyes");
		pojoTest.setEdad(25);
		
		boolean validateOptional = false;
		
		Optional<Map<String, List<String>>> fails = validator.validation(pojoTest, validateOptional);
		
		Assert.assertTrue(fails.isPresent() && fails.get().size() == 0);
	}
	
	@Test
	public void testNotNullOptionalLenght_ObjectFull_True() {
		
		Pojo pojoTest = new Pojo();
		pojoTest.setNombre("jairo");
		pojoTest.setApellido("Reyes");
		pojoTest.setEdad(25);
		
		boolean validateOptional = true;
		
		Optional<Map<String, List<String>>> fails = validator.validation(pojoTest, validateOptional);
		
		Assert.assertTrue(fails.isPresent() && fails.get().size() == 0);
	}
	
	@Test
	public void testNotNullOptionalLenght_ObjectEmpty_True() {
		
		Pojo pojoTest = new Pojo();
		
		boolean validateOptional = true;
		
		Optional<Map<String, List<String>>> fails = validator.validation(pojoTest, validateOptional);
		
		if (fails.isPresent()) {
			printFault(fails.get());
			Assert.assertTrue(fails.get().size() >0);
		}
		
		Assert.fail();
		
			
	}

	
	private void printFault(Map<String, List<String>> methods) {
		for (String method : methods.keySet()) {
			System.out.println(">Method: " + method);
			
			for (String fault : methods.get(method)) {
				System.out.println(">> fault: " + fault);
				
			}
		}
	}
}
