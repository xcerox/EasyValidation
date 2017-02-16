package org.doit.project.easyvalidation.interfaces.impl;

import org.doit.project.easyvalidation.consts.LoggerMessage;
import org.doit.project.easyvalidation.exceptions.InternalException;
import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.project.easyvalidation.util.ValidationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotNullValidator implements Validator<Object>{

	private static final Logger LOGGER = LoggerFactory.getLogger(NotNullValidator.class);
	
	public NotNullValidator() {
		super();
		System.out.println("Creando NotNullValidator");
	}

	@Override
	public boolean isValid(Object value, JSONObject propierties) throws InternalException, JSONException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "value", value);
		LOGGER.debug(LoggerMessage.PARAMETER, "propierties", propierties);
		
		LOGGER.debug("Validating is null");
		boolean isValid = !ValidationUtil.isNull(value);
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, isValid);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return isValid;
	}


}
