package org.doit.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.easyvalidation.consts.Empty;
import org.doit.easyvalidation.interfaces.impl.LenghtValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = LenghtValidator.class, id = "DOIT@LENGH_VALIDATOR")
public @interface Lenght {
	
	@Property
	public int min() default Empty.INTEGER;
	
	@Property
	public int max() default Empty.INTEGER;
}
