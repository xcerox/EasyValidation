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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationCore {
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidationCore.class);
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

	/**
	 * Validate methods gets finder
	 * @param pojo
	 * @param methods
	 * @param validateOptional
	 * @return methods with faults
	 * @throws InternalException
	 */
	private Map<String, List<String>> validMethodsGet(final Object pojo, final Collection<Method> methods, boolean validateOptional)
			throws InternalException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "pojo", pojo);
		LOGGER.debug(LoggerMessage.PARAMETER, "methods", methods);
		LOGGER.debug(LoggerMessage.PARAMETER, "validateOptional", validateOptional);
		
		Map<String, List<String>> methodsFail = new HashMap<>();
		
		LOGGER.debug("Iterating methods");
		for (Method currentMethod : methods) {
			
			LOGGER.debug("validating is method opcional");
			if (currentMethod.isAnnotationPresent(IsOptional.class)
					&& !validateOptional) {
				LOGGER.debug("skip method opcional {}", currentMethod.getName());
				continue;
			}
			
			LOGGER.debug("fetch fauls for this method {}", currentMethod.getName());
			List<String> faults = getfaults(pojo, currentMethod);
			
			LOGGER.debug("validating existing faults");
			if (!ValidationUtil.isNullOrEmpty(faults)) {
				LOGGER.debug("add method with faults");
				methodsFail.put(currentMethod.getName(), faults);
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, methodsFail);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return methodsFail;
	}
	
	/**
	 * Return faults for a method
	 * @param pojo
	 * @param method
	 * @return faults
	 * @throws InternalException
	 */
	private List<String> getfaults(final Object pojo, final Method method) 
			throws InternalException {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "pojo", pojo);
		LOGGER.debug(LoggerMessage.PARAMETER, "method", method);
		
		List<String> faults = new ArrayList<>();
		
		LOGGER.debug("Filter annotations for annotation Validation");
		List<Annotation> annotationsFiltered = filterAnnotation(method.getAnnotations());
		
		LOGGER.debug("Validating annotations filtered");
		if (!ValidationUtil.isNullOrEmpty(annotationsFiltered)) {
			
			LOGGER.debug("fetch value of method");
			Object returnValue = fetchReturnValue(pojo, method);
			
			LOGGER.debug(LoggerMessage.PARAMETER, "returnValue", returnValue);
			
			for (Annotation annotation : annotationsFiltered) {
				
				LOGGER.debug("find annotation Validation");
				Validation validation = annotation.annotationType().getAnnotation(Validation.class);
				
				LOGGER.debug("Instance validator");
				Validator<Object> validator = instanceValidator(validation.value());
				
				LOGGER.debug("fetch properties");
				JSONObject properties = fetchPropierties(annotation);
				
				LOGGER.debug(LoggerMessage.PARAMETER, "properties", properties);
				
				try {
					
					LOGGER.debug("Validate method with annotationId {}", validation.id());
					if (!validator.isValid(returnValue, properties)) {
						LOGGER.debug("add annotation like fault");
						faults.add(validation.id());
					}
				} catch (JSONException e) {
					LOGGER.error(e.getMessage(), e);
					throw new InternalException();
				}
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, faults);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return faults;
	}
	
	/**
	 * Return new instace 
	 * @param clazz
	 * @return new Instance of Validator<Object>
	 * @throws InternalException
	 */
	@SuppressWarnings("unchecked")
	private Validator<Object> instanceValidator(Class<?> clazz) 
			throws InternalException{
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "clazz", clazz);
		
		Validator<Object> validator = null;
		LOGGER.debug("Validating exist instance");
		if (poolInstances.containsKey(clazz)) {
			LOGGER.debug("Return instance recycled");
			validator = poolInstances.get(clazz);
			
		} else {
			try {
				LOGGER.debug("Instance new Validator");
				validator = (Validator<Object>) clazz.newInstance();
				 
				LOGGER.debug("put reference in pool");
				poolInstances.put(clazz, validator);
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error(e.getMessage(), e);
				throw new InternalException();
			}
		}
		
		LOGGER.debug(LoggerMessage.RETURN_METHOD, validator);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return validator;
	}

	/**
	 * Return annotations filter by Validation
	 * @param annotations
	 * @return annotation has present Validation
	 */
	private List<Annotation> filterAnnotation(Annotation[] annotations) {
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "annotations", annotations);
		
		List<Annotation> annotationsFiltered = new ArrayList<>();

		LOGGER.debug("Iterating annotations");
		for (Annotation annotation : annotations) {
			
			LOGGER.debug("Validating is present Validation");
			if (annotation.annotationType().isAnnotationPresent(Validation.class)) {
				LOGGER.debug("Add annotations");
				annotationsFiltered.add(annotation);
			}
		}

		LOGGER.debug(LoggerMessage.RETURN_METHOD, annotationsFiltered);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
		return annotationsFiltered;
	}

	/**
	 * Return property of annotation with it value
	 * @param annotation
	 * @return propery for Validations
	 * @throws InternalException
	 */
	private JSONObject fetchPropierties(Annotation annotation) 
			throws InternalException{
		LOGGER.trace(LoggerMessage.ENTER_METHOD);
		LOGGER.debug(LoggerMessage.PARAMETER, "annotation", annotation);
		
		JSONObject propierties = new JSONObject();
		
		LOGGER.debug("Iteranting methods of annotations");
		for (Method currentMethod : annotation.annotationType().getMethods()) {
			
			LOGGER.debug("get annotation Property");
			Property property = currentMethod.getAnnotation(Property.class);
			
			LOGGER.debug("Validating annotation");
			if (!ValidationUtil.isNull(property)) {
				
				String propertyName = property.value();
				
				LOGGER.debug("Validating propertyName is for default in annotation");
				if (propertyName.equals(Empty.STRING)) {
					
					LOGGER.debug("Asigning name of method: {}", currentMethod.getName());
					propertyName = currentMethod.getName();
				}
				 
				LOGGER.debug("fetch value on method");
				Object value = fetchReturnValue(annotation, currentMethod);
				
				LOGGER.debug(LoggerMessage.PARAMETER, "value return", value);
				try{
					LOGGER.debug("put property name and value");
					propierties.put(propertyName, value);		
				} catch (JSONException e) {
					LOGGER.error(e.getMessage(), e);
					throw new InternalException();
				}
			}
		}

		LOGGER.debug(LoggerMessage.RETURN_METHOD, propierties);
		LOGGER.trace(LoggerMessage.EXIT_METHOD);
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

