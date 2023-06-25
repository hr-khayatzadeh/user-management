package de.trustedshops.user.management.validation;

import java.util.Locale;
import java.util.Set;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CountryCodeValidator implements ConstraintValidator<CountryCode, String > {


	@Override
	public boolean isValid(String s, ConstraintValidatorContext ctx) {
		var isoCountries = Set.of(Locale.getISOCountries());
		return isoCountries.contains(s);
	}
}
