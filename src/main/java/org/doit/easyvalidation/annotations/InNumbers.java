package org.doit.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.easyvalidation.interfaces.impl.InNumbersValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = InNumbersValidator.class, id=InNumbers.ID)
public @interface InNumbers {

	public static final String ID = "DOIT@IN_NUMBERS";
	public static final String MULTIPLE_VALUES = "values";
	
	@Property(MULTIPLE_VALUES)
	public long[] values();
}
