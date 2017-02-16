package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.exceptions.InternalException;
import org.doit.project.easyvalidation.interfaces.Validator;
import org.json.JSONException;
import org.json.JSONObject;

public class InNumbersValidator implements Validator<Long>{

	public InNumbersValidator() {
		super();
	}

	@Override
	public boolean isValid(Long value, JSONObject propierties) throws InternalException, JSONException {
		// TODO Auto-generated method stub
		return false;
	}

}
