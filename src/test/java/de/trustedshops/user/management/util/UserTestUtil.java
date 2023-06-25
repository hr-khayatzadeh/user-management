package de.trustedshops.user.management.util;

import java.util.List;

import de.trustedshops.user.management.model.User;
import de.trustedshops.user.management.model.UserAddress;
import de.trustedshops.user.management.model.UserStatus;

public class UserTestUtil {

	public static User buildUser3() {
		return User.builder()
				.username("arron.mueller")
				.firstName("Aaron")
				.lastName("Mueller")
				.email("arron.muelller@gmail.com")
				.status(UserStatus.REQUIRED_CHANGE_PASSWORD)
				.userAddress(
						UserAddress.builder()
								.street("Ehrendfeld str. 80")
								.city("Cologne")
								.postalCode("50282")
								.province("NRW")
								.countryCode("DE")
								.build())
				.build();
	}

	public static User buildUser2() {
		return User.builder()
				.username("hans.schneider")
				.firstName("Hans")
				.lastName("Schneider")
				.email("hans.schneider@gmail.com")
				.status(UserStatus.DISABLED)
				.userAddress(
						UserAddress.builder()
								.street("Leipziger str. 11")
								.city("Cologne")
								.postalCode("50858")
								.province("NRW")
								.countryCode("DE")
								.build())
				.build();
	}

	public static User buildUser1() {
		return User.builder()
				.username("hr.schneider")
				.firstName("Hamidreza")
				.lastName("Khayatzadeh")
				.email("hr.khayatzadeh@gmail.com")
				.status(UserStatus.ENABLED)
				.userAddress(
						UserAddress.builder()
								.street("Am Wachberg 80")
								.city("Erftstadt")
								.postalCode("50374")
								.province("NRW")
								.countryCode("DE")
								.build())
				.build();
	}

	public static List<User> fetchAllUsers() {
		return List.of(buildUser1(), buildUser2(), buildUser3());
	}

}
