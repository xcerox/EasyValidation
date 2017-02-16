package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.project.easyvalidation.consts.Empty;
import org.doit.projectvalidation.interfaces.impl.SizeValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = SizeValidator.class, id = "SIZE_VALIDATOR")
public @interface Size {
	
	@Property
	public long	min() default Empty.INTEGER;
	
	@Property
	public long max() default Empty.INTEGER;
}
	