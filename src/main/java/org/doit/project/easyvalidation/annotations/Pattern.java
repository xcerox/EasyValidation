package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.projectvalidation.consts.Empty;
import org.doit.projectvalidation.interfaces.impl.PatternValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = PatternValidator.class, id = "PATTERN_VALIDATION")
public @interface Pattern {
	
	@Property
	public String regex() default Empty.STRING;
}
