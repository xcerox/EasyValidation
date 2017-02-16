package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class LenghtValidator implements Validator<String>{

	public LenghtValidator() {
		super();
	}

	@Override
	public void doValidate(String tipo, JSONObject propierties)
			throws ValidationIncorrectException, ValidationFailException {
		System.out.println("Entering doValidate");
		
		Integer min = null;
		Integer max = null;
		
		try {
			min = propierties.getInt("min")>0?propierties.getInt("min"):null;
			max = propierties.getInt("max")>0?propierties.getInt("max"):null;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (min != null) {
			if (tipo.length() < min) {
				System.out.println("Es menor");
			}
		}
		
		if (max != null) {
			if (tipo.length() > max) {
				System.out.println("Es mayor");
			}
		}

	}
}
