package org.doit.easyvalidation.interfaces.impl;

import org.doit.easyvalidation.annotations.InNumbers;
import org.doit.easyvalidation.consts.LoggerMessage;
import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.interfaces.Validator;
import org.doit.easyvalidation.util.ValidationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InNumbersValidator implements Validator<Object>{

	private static final Logger LOGGER = LoggerFactory.getLogger(InNumbersValidator.class);
	
	public InNumbersValidator() {
		super();
	}

	@Override
	public boolean isValid(Object value, JSONObject propierties) throws InternalException, JSONException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "value", value);
		LOGGER.debug(LoggerMessage.PARAMETER, "propierties", propierties);
		boolean isValid = false;
		
		Number number = (Number)value;
		LOGGER.info("value converted in number {}", number);
		
		long[] options = (long[]) propierties.get(InNumbers.MULTIPLE_VALUES);
		
		for (long option : options) {
			if (!ValidationUtil.isNull(value) 
					&& number.longValue() == option) {
				isValid = true;
				break;
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, isValid);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return isValid;
	}

}
