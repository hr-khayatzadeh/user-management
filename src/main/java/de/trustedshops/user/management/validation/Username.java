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
@Constraint(validatedBy = UserValidator.class)
public @interface Username {
	String message() default "Invalid username: "
			+ "Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.\n"
			+ "Username allowed of the dot (.), underscore (_), and hyphen (-).\n"
			+ "The dot (.), underscore (_), or hyphen (-) must not be the first or last character.\n"
			+ "The dot (.), underscore (_), or hyphen (-) does not appear consecutively,\n"
			+ "The number of characters must be between 5 to 20.";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};

}
