package org.doit.easyvalidation.interfaces.impl;

import org.doit.easyvalidation.consts.Empty;
import org.doit.easyvalidation.consts.LoggerMessage;
import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.interfaces.Validator;
import org.doit.easyvalidation.util.ValidationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LenghtValidator implements Validator<String> {
	private static final Logger LOGGER = LoggerFactory.getLogger(LenghtValidator.class);

	public LenghtValidator() {
		super();
	}

	@Override
	public boolean isValid(String value, JSONObject propierties) throws InternalException, JSONException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "value", value);
		LOGGER.debug(LoggerMessage.PARAMETER, "propierties", propierties);

		LOGGER.debug("validating it value is null");
		if (ValidationUtil.isNull(value)) {
			LOGGER.debug("it value is null");
			LOGGER.debug(LoggerMessage.RETURN_METHOD, false);
			LOGGER.trace(LoggerMessage.EXIT_METHOD);

			return false;
		}

		LOGGER.debug("get values in properties");
		int max = propierties.getInt("max");
		int min = propierties.getInt("min");

		boolean esValidMin = true;
		boolean esValidMax = true;

		LOGGER.debug("Validating min");
		if (Empty.INTEGER != min && value.length() < min) {
			LOGGER.debug("fail of min");
			esValidMin = false;
		}

		LOGGER.debug("Validating max");
		if (Empty.INTEGER != max && value.length() > max) {
				LOGGER.debug("fail of max");
				esValidMax = false;
		}

		LOGGER.debug(LoggerMessage.RETURN_METHOD, esValidMin && esValidMax);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return esValidMin && esValidMax;
	}

}
