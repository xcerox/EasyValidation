package org.doit.easyvalidation.interfaces.impl;

import java.util.Collection;

import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.interfaces.Validator;
import org.json.JSONException;
import org.json.JSONObject;

public class NotEmptyValidator implements Validator<Collection<?>>{

	public NotEmptyValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isValid(Collection<?> value, JSONObject propierties) throws InternalException, JSONException {
		// TODO Auto-generated method stub
		return false;
	}

}
