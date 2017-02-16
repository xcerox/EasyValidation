package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.projectvalidation.exceptions.ValidationFailException;
import org.doit.projectvalidation.exceptions.ValidationIncorrectException;
import org.json.JSONObject;

public class NotNullValidator implements Validator<Object>{

	public NotNullValidator() {
		super();
		System.out.println("Creando NotNullValidator");
	}

	@Override
	public void doValidate(Object value, JSONObject propierties) throws ValidationIncorrectException, ValidationFailException {
		System.out.println("Entering doValidate");
		
		if(value == null){
			System.out.println("is null");
			
		} else {
			System.out.println("is not null");
		}
	}

}
