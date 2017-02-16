package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class InStringsValidator implements Validator<String>{

	public InStringsValidator() {
		super();
	}

	@Override
	public void doValidate(String tipo, JSONObject propierties)
			throws ValidationIncorrectException, ValidationFailException {
		
	}

}
