package org.doit.project.easyvalidation.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.doit.project.easyvalidation.annotations.IsOptional;
import org.doit.project.easyvalidation.annotations.Property;
import org.doit.project.easyvalidation.annotations.Validation;
import org.doit.project.easyvalidation.consts.Empty;
import org.doit.project.easyvalidation.consts.LoggerMessage;
import org.doit.project.easyvalidation.exceptions.InternalException;
import org.doit.project.easyvalidation.exceptions.InternalValidationException;
import org.doit.project.easyvalidation.interfaces.Validator;
import org.doit.project.easyvalidation.util.ValidationUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

public class ValidationCore {
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ValidationCore.class);
	private static final String GET = "get";
	private static final String IS = "is";
	private Map<Class<?>, Validator<Object>> poolInstances;
	
	{
		poolInstances = new HashMap<>();
	}
	
	public ValidationCore() {
		super();
	}

	public Optional<Map<String, List<String>>> validation(final Object pojo, boolean validateOptional) 
			throws InternalValidationException {

		Collection<Method> methods = new HashSet<>();
		Map<String, List<String>> fails = null;
		
		try {

			findMethodsGet(pojo, methods);			
			fails = validMethodsGet(pojo, methods, validateOptional);
			
		} catch (InstantiationException | IllegalAccessException | InternalException e) {

			throw new InternalValidationException();
		}

		return Optional.of(fails);

	}

	/**
	 * Find all method get in pjo and in super class.
	 * @param pojo
	 * @param methods
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void findMethodsGet(final Object pojo, Collection<Method> methods)
			throws InstantiationException, IllegalAccessException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "pojo", pojo);
		LOGGER.debug(LoggerMessage.PARAMETER, "methods", methods);
		
		LOGGER.debug("Validating if superclass of pojo is diff object and not is abstract");
		if (!pojo.getClass().getSuperclass().equals(Object.class) 
				&& !Modifier.isAbstract(pojo.getClass().getSuperclass().getModifiers())) {
			
			LOGGER.debug("instancing super class");	
			Object superPojo = pojo.getClass().getSuperclass().newInstance();
			
			findMethodsGet(superPojo, methods);
		}

		LOGGER.debug("Iterating methods finders");
		for (Method currentMethod : pojo.getClass().getMethods()) {
			
			LOGGER.debug("validating its method get");
			if (isMethodGet(currentMethod)){
				
				LOGGER.debug("Add method {}", currentMethod.getName());
				methods.add(currentMethod);
			}
		}
		
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
	}
	
	/**
	 * Return is Method getter
	 * @param Method
	 * @return true | false
	 */
	private boolean isMethodGet(final Method method){
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "method", method);
		
		boolean isGet = false;
		
		LOGGER.debug("Identify is method start with get or is");
		if (method.getName().startsWith(GET) || method.getName().startsWith(IS)) {

			LOGGER.debug("Identify is method not static, public and return something");
			if (!Modifier.isStatic(method.getModifiers()) && Modifier.isPublic(method.getModifiers())
					&& method.getParameterTypes().length == Empty.INTEGER
					&& method.getReturnType() != null) {
				isGet = true; 
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, isGet);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return isGet;
	}

	private Map<String, List<String>> validMethodsGet(final Object pojo, final Collection<Method> methods, boolean validateOptional)
			throws InternalException {

		Map<String, List<String>> methodsFail = new HashMap<>();
		
		for (Method currentMethod : methods) {

			if (currentMethod.isAnnotationPresent(IsOptional.class)
					&& !validateOptional) {
				continue;
			}
			
			List<String> faults = getfaults(pojo, currentMethod);
			
			if (!ValidationUtil.isNullOrEmpty(faults)) {
				methodsFail.put(currentMethod.getName(), faults);
			}
		}
		
		return methodsFail;
	}
	
	private List<String> getfaults(final Object pojo, final Method method) 
			throws InternalException {
		List<String> faults = new ArrayList<>();
		
		List<Annotation> annotationsFiltered = filterAnnotation(method.getAnnotations());
		
		if (!ValidationUtil.isNullOrEmpty(annotationsFiltered)) {
			Object returnValue = fetchReturnValue(pojo, method);
			
			for (Annotation annotation : annotationsFiltered) {
				
				Validation validation = annotation.annotationType().getAnnotation(Validation.class);
				
				Validator<Object> validator = instanceValidator(validation.value());
				
				JSONObject properties = fetchPropierties(annotation);
				System.out.println("properties: " + properties);
				
				try {
					if (validator.doValidate(returnValue, properties)) {
						faults.add(validation.id());
					}
				} catch (JSONException e) {
					throw new InternalException();
				}
			}
		}
		
		return faults;
	}
	
	@SuppressWarnings("unchecked")
	private Validator<Object> instanceValidator(Class<?> clazz) throws InternalException{
		
		if (poolInstances.containsKey(clazz)) {
			return poolInstances.get(clazz);
		} else {
			try {
				Validator<Object> validator = (Validator<Object>) clazz.newInstance();
				poolInstances.put(clazz, validator);
				return validator;
			} catch (InstantiationException | IllegalAccessException e) {
				throw new InternalException();
			}
		}
	}

	private List<Annotation> filterAnnotation(Annotation[] annotations) {
		List<Annotation> annotationsFiltered = new ArrayList<>();

		for (Annotation annotation : annotations) {
			if (annotation.annotationType().isAnnotationPresent(Validation.class)) {
				annotationsFiltered.add(annotation);
			}
		}

		return annotationsFiltered;
	}

	private JSONObject fetchPropierties(Annotation annotation) throws InternalException{
		JSONObject propierties = new JSONObject();
		
		for (Method currentMethod : annotation.annotationType().getMethods()) {
			
			Property property = currentMethod.getAnnotation(Property.class);
			
			if (!ValidationUtil.isNull(property)) {
				
				String propertyName = property.value();
				
				if (propertyName.equals(Empty.STRING)) {
					propertyName = currentMethod.getName();
				}
				 
				Object value = fetchReturnValue(annotation, currentMethod);
				
				try{
					propierties.put(propertyName, value);		
				} catch (JSONException e) {
					throw new InternalException();
				}
			}
		}

		return propierties;
	}
	
	/**
	 * Fetch the value in method
	 * @param object
	 * @param method
	 * @return {@link Object method}
	 * @throws InternalException
	 */
	private Object fetchReturnValue(Object object, Method method) throws InternalException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "object", object);
		LOGGER.debug(LoggerMessage.PARAMETER, "method", method);
		
		try {
			LOGGER.debug("Execute Method: {}", method.getName());
			Object value = method.invoke(object, new Object[0]);
			
			LOGGER.debug(LoggerMessage.RETURN_METHOD, value);
			return value;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			LOGGER.error(e.getMessage(), e);
			throw new InternalException();
			
		} finally {
			LOGGER.trace(LoggerMessage.EXIT_METHOD);
		}

	}
}

