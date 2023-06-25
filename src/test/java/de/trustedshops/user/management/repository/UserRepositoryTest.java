package de.trustedshops.user.management.repository;

import static de.trustedshops.user.management.util.UserTestUtil.buildUser1;
import static de.trustedshops.user.management.util.UserTestUtil.buildUser2;
import static de.trustedshops.user.management.util.UserTestUtil.buildUser3;
import static de.trustedshops.user.management.util.UserTestUtil.fetchAllUsers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.TransactionSystemException;

import de.trustedshops.user.management.model.User;
import de.trustedshops.user.management.model.UserAddress;
import de.trustedshops.user.management.model.UserStatus;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
@Profile("test")
public class UserRepositoryTest {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserAddressRepository userAddressRepository;

	@BeforeEach
	void init() {
		userRepository.deleteAll();
	}

	@ParameterizedTest
	@MethodSource("getUsers")
	void shouldPersistDifferentUser(User user) {
		userAddressRepository.save(user.getUserAddress());
		userRepository.save(user);
		var persistedUser = userRepository.findById(user.getId());
		assertTrue(persistedUser.isPresent());
		assertAll(
				() -> assertNotNull(persistedUser.get().getId()),
				() -> assertNotNull(persistedUser.get().getUsername()),
				() -> assertNotNull(persistedUser.get().getEmail()),
				() -> assertNotNull(persistedUser.get().getFirstName()),
				() -> assertNotNull(persistedUser.get().getLastName()),
				() -> assertNotNull(persistedUser.get().getStatus()),
				() -> assertNotNull(persistedUser.get().getCreatedAt()),
				() -> assertNotNull(persistedUser.get().getLastUpdatedAt()),
				() -> assertNotNull(persistedUser.get().getVersion()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getId()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getStreet()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getCity()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getProvince()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getPostalCode()),
				() -> assertNotNull(persistedUser.get().getUserAddress().getCountryCode())
		);
	}

	@Test
	void shouldValidateWrongUsername() {
		var user = User.builder()
				.username("abc")
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
		userAddressRepository.save(user.getUserAddress());
		var ex = assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
		assertTrue(ExceptionUtils.getRootCause(ex) instanceof ConstraintViolationException);
	}

	@Test
	void shouldValidateWrongCountryCode() {
		var user = buildUser1();
		user.getUserAddress().setCountryCode("DEU");
		var ex = assertThrows(TransactionSystemException.class, () -> userAddressRepository.save(user.getUserAddress()));
		assertTrue(ExceptionUtils.getRootCause(ex) instanceof ConstraintViolationException);
	}

	@Test
	void shouldValidateWrongEmailFormat() {
		var user = buildUser1();
		user.setEmail("abc@");
		userAddressRepository.save(user.getUserAddress());
		var ex = assertThrows(TransactionSystemException.class, () -> userRepository.save(user));
		assertTrue(ExceptionUtils.getRootCause(ex) instanceof ConstraintViolationException);
	}

	@Test
	void shouldValidateDuplicateUsername() {
		var user1 = buildUser1();
		userAddressRepository.save(user1.getUserAddress());
		userRepository.save(user1);
		var user2 = buildUser2();
		user2.setUsername(user1.getUsername());
		userAddressRepository.save(user2.getUserAddress());
		var ex = assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user2));
		assertTrue(ExceptionUtils.getRootCause(ex) instanceof JdbcSQLIntegrityConstraintViolationException);
	}

	@Test
	void shouldFindAnUniqueUsername() {
		fetchAllUsers().forEach(p -> {
			userAddressRepository.save(p.getUserAddress());
			userRepository.save(p);
		});
		Optional<User> selectedUser = fetchAllUsers().stream().findAny();
		var user = userRepository.findUserByUsername(selectedUser.get().getUsername());
		assertTrue(user.isPresent());
		Assertions.assertEquals(selectedUser.get().getUsername(), user.get().getUsername());
	}

	@Test
	void shouldFindTwoUsersLikeParam() {
		fetchAllUsers().forEach(p -> {
			userAddressRepository.save(p.getUserAddress());
			userRepository.save(p);
		});
		var pageableUsers = userRepository.findUsersByUsernameContainingOrderByUsername("schneider", PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 2);
	}

	@ParameterizedTest
	@EnumSource(UserStatus.class)
	void shouldFindOnlyOneUserPerStatus(UserStatus userStatus) {
		fetchAllUsers().forEach(p -> {
			userAddressRepository.save(p.getUserAddress());
			userRepository.save(p);
		});
		var pageableUsers = userRepository.findUsersByStatus(userStatus, PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 1);
	}

	public static Stream<Arguments> getUsers() {
		return Stream.of(
				Arguments.of(
						buildUser1()
				),
				Arguments.of(
						buildUser2()
				),
				Arguments.of(
						buildUser3()
				)
		);
	}
}
