package org.doit.easyvalidation.interfaces.impl;

import org.doit.easyvalidation.annotations.InStrings;
import org.doit.easyvalidation.consts.LoggerMessage;
import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.interfaces.Validator;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InStringsValidator implements Validator<String>{

	private static final Logger LOGGER = LoggerFactory.getLogger(InStringsValidator.class);
	
	public InStringsValidator() {
		super();
	}

	@Override
	public boolean isValid(String value, JSONObject propierties) throws InternalException, JSONException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "value", value);
		LOGGER.debug(LoggerMessage.PARAMETER, "propierties", propierties);
		boolean isValid = false;
		
		String[] options = (String[]) propierties.get(InStrings.MULTIPLE_VALUES);
		
		for (String option : options) {
			if (option.equals(value)) {
				isValid = true;
				break;
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, isValid);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return isValid;
	}
}
