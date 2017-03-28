package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.project.easyvalidation.consts.Empty;
import org.doit.project.easyvalidation.interfaces.impl.SizeValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = SizeValidator.class, id = "DOIT@SIZE_VALIDATOR")
public @interface Size {
	
	@Property
	public int min() default Empty.INTEGER;
	
	@Property
	public int max() default Empty.INTEGER;
}
	