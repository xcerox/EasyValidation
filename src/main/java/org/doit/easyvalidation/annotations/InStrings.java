package org.doit.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.easyvalidation.consts.Empty;
import org.doit.easyvalidation.interfaces.impl.InStringsValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = InStringsValidator.class, id= InStrings.ID)
public @interface InStrings {
	
	public static final String ID = "DOIT@IN_STRINGS";
	public static final String MULTIPLE_VALUES = "values";
	
	@Property(MULTIPLE_VALUES)
	public String[] values() default {Empty.STRING};
}
