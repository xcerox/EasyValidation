package org.doit.easyvalidation.core;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.doit.easyvalidation.consts.LoggerMessage;
import org.doit.easyvalidation.exceptions.InternalException;
import org.doit.easyvalidation.exceptions.InternalValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationCore {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationCore.class);
	private ValidationCoreHelper coreHelper;

	private Map<String, List<String>> fails;

	public ValidationCore() {
		super();
		coreHelper = new ValidationCoreHelper();
	}

	/**
	 * Validate pojo using annotacion
	 * 
	 * @param pojo
	 * @param validateOptional
	 * @return TRUE | FALSE
	 * @throws InternalValidationException
	 */
	public boolean validation(final Object pojo, boolean validateOptional) throws InternalValidationException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "pojo", pojo);
		LOGGER.debug(LoggerMessage.PARAMETER, "validateOptional", validateOptional);

		Collection<Method> methods = new HashSet<>();
		fails = new HashMap<>();

		try {
			LOGGER.info("finding methos get");
			coreHelper.findMethodsGet(pojo, methods);

			LOGGER.info("getting fail in validations");
			fails = coreHelper.validMethodsGet(pojo, methods, validateOptional);

		} catch (InstantiationException | IllegalAccessException | InternalException e) {
			LOGGER.error(e.getMessage(), e);
			throw new InternalValidationException();
		}

		LOGGER.debug(LoggerMessage.RETURN_METHOD, fails.isEmpty());
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return fails.isEmpty();
	}

	/**
	 * Return Fails
	 * 
	 * @return Fails
	 */
	public Optional<Map<String, List<String>>> getFails() {
		return Optional.of(fails);
	}

	/**
	 * Return Fauls
	 * 
	 * @return Fauls
	 */
	public Optional<Collection<String>> getFauls() {
		Set<String> fauls = new HashSet<>();

		if (!fails.isEmpty()) {

			for (Map.Entry<String, List<String>> row : fails.entrySet()) {
				fauls.addAll(row.getValue());
			}
		}

		return Optional.of(fauls);
	}
}
