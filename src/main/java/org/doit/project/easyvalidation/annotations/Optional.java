package org.doit.project.easyvalidation.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.doit.projectvalidation.consts.Empty;

@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface Optional {
	public boolean checked() default Empty.BOOLEAN;
}
