package com.hospitalsearch.service.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Retention(RUNTIME)
@Target({TYPE, FIELD})
@Constraint(validatedBy = UniquTokenMinMaxValidator.class)
public @interface UniquTokenMinMax {

	String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
