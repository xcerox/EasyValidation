package org.doit.project.easyvalidation.interfaces.impl;

import java.util.Collection;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class NotEmptyValidator implements Validator<Collection<?>>{

	public NotEmptyValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doValidate(Collection<?> tipo, JSONObject propierties)
			throws ValidationIncorrectException, ValidationFailException {
		// TODO Auto-generated method stub
		
	}

}
