package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class InNumbersValidator implements Validator<Long>{

	public InNumbersValidator() {
		super();
	}

	@Override
	public void doValidate(Long value, JSONObject propierties) throws ValidationIncorrectException, ValidationFailException {
		      
	}

}
