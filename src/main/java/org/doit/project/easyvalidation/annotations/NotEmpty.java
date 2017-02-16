package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.projectvalidation.interfaces.impl.NotEmptyValidator;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
@Validation(value = NotEmptyValidator.class, id = "NOT_EMPTY_VALIDATOR")
public @interface NotEmpty {

}
