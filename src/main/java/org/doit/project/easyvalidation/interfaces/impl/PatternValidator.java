package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.exceptions.InternalException;
import org.doit.project.easyvalidation.interfaces.Validator;
import org.json.JSONException;
import org.json.JSONObject;

public class PatternValidator implements Validator<String>{

	public PatternValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(String value, JSONObject propierties) throws InternalException, JSONException {
		// TODO Auto-generated method stub
		return false;
	}
}
