package org.doit.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.easyvalidation.consts.Empty;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface IsOptional {
	public boolean checked() default Empty.BOOLEAN;
}
