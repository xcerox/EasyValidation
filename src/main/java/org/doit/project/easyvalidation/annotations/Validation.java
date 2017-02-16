package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.projectvalidation.interfaces.Validator;

@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Validation {
	public Class<? extends Validator<?>> value();
	public String id();
}