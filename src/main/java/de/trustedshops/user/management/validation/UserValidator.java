package de.trustedshops.user.management.validation;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserValidator implements ConstraintValidator<Username, String> {

	private static final String USERNAME_PATTERN =
			"^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,18}[a-zA-Z0-9]$";

	private static final Pattern pattern = Pattern.compile(USERNAME_PATTERN);

	@Override
	public boolean isValid(String s, ConstraintValidatorContext ctx) {
		var matcher = pattern.matcher(s);
		return matcher.matches();
	}
}
