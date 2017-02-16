package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class PatternValidator implements Validator<String>{

	public PatternValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doValidate(String tipo, JSONObject propierties)
			throws ValidationIncorrectException, ValidationFailException {
		// TODO Auto-generated method stub
		
	}

}
