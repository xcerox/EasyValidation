package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.project.easyvalidation.interfaces.impl.InNumbersValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = InNumbersValidator.class, id="DOIT@IN_NUMBERS")
public @interface InNumbers {
	
	@Property
	public long[] values();
}
