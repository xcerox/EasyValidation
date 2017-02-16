package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.project.easyvalidation.consts.Empty;
import org.doit.projectvalidation.interfaces.impl.InStringsValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = InStringsValidator.class, id="IN_STRINGS")
public @interface InStrings {
	
	@Property
	public String[] values() default {Empty.STRING};
}
