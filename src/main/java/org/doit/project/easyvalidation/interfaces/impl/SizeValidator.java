package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class SizeValidator implements Validator<Long> {

	public SizeValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doValidate(Long tipo, JSONObject propierties)
			throws ValidationIncorrectException, ValidationFailException {
		// TODO Auto-generated method stub
		
	}

}
