package de.trustedshops.user.management.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target( { FIELD, PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CountryCodeValidator.class)
public @interface CountryCode {
	String message() default "Invalid country code: must be defined a ISO 3166 country code";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
