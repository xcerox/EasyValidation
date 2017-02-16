package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.projectvalidation.interfaces.impl.NotNullValidator;

@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = NotNullValidator.class, id = "NOT_NULL")
public @interface NotNull {
	
}
