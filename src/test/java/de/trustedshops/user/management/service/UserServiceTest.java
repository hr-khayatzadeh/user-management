package de.trustedshops.user.management.service;

import static de.trustedshops.user.management.util.UserTestUtil.fetchAllUsers;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import de.trustedshops.user.management.exception.UserNotFoundException;
import de.trustedshops.user.management.repository.UserAddressRepository;
import de.trustedshops.user.management.repository.UserRepository;

@SpringBootTest
public class UserServiceTest {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserAddressRepository userAddressRepository;

	@BeforeEach
	void init() {
		userRepository.deleteAll();
		fetchAllUsers().forEach(p -> {
			userAddressRepository.save(p.getUserAddress());
			userRepository.save(p);
		});
	}

	@Test
	void shouldSearchUsersByUsernameReturnsTwoUsers() {
		var pageableUsers = userService.searchUsersByUsername("schneider", PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 2);
		pageableUsers.stream().forEach(p -> {
			var userAddress = p.getAddress();
			assertAll(() -> {
				assertNotNull(p.getId());
				assertNotNull(p.getUsername());
				assertNotNull(p.getEmail());
				assertNotNull(p.getFirstName());
				assertNotNull(p.getLastName());
				assertNotNull(p.getStatus());
				assertNotNull(p.getCreatedAt());
				assertNotNull(p.getLastUpdatedAt());
				assertNotNull(userAddress.getId());
				assertNotNull(userAddress.getStreet());
				assertNotNull(userAddress.getCity());
				assertNotNull(userAddress.getProvince());
				assertNotNull(userAddress.getPostalCode());
				assertNotNull(userAddress.getCountryCode());
				assertNotNull(userAddress.getCreatedAt());
				assertNotNull(userAddress.getLastUpdatedAt());
			});
		});
	}

	@Test
	void shouldSearchUsersByUsernameReturnsEmpty() {
		var pageableUsers = userService.searchUsersByUsername("test", PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 0);
	}

	@Test
	void shouldFindUserByEmailReturnsOneUser() {
		var user = userService.getUserByEmailAddress("hr.khayatzadeh@gmail.com");
		var userAddress = user.getAddress();
		assertAll(() -> {
			assertNotNull(user.getId());
			assertNotNull(user.getUsername());
			assertNotNull(user.getEmail());
			assertNotNull(user.getFirstName());
			assertNotNull(user.getLastName());
			assertNotNull(user.getStatus());
			assertNotNull(user.getCreatedAt());
			assertNotNull(user.getLastUpdatedAt());
			assertNotNull(userAddress.getId());
			assertNotNull(userAddress.getStreet());
			assertNotNull(userAddress.getCity());
			assertNotNull(userAddress.getProvince());
			assertNotNull(userAddress.getPostalCode());
			assertNotNull(userAddress.getCountryCode());
			assertNotNull(userAddress.getCreatedAt());
			assertNotNull(userAddress.getLastUpdatedAt());
		});
	}

	@Test
	void shouldFindUserByEmailThrowsUserNotFoundException() {
		assertThrows(UserNotFoundException.class, () -> userService.getUserByEmailAddress("test@test.com"));
	}

	@Test
	void shouldFindUserByUsernameReturnsOneUser() {
		var user = userService.getUserByUsername("hr.schneider");
		var userAddress = user.getAddress();
		assertAll(() -> {
			assertNotNull(user.getId());
			assertNotNull(user.getUsername());
			assertNotNull(user.getEmail());
			assertNotNull(user.getFirstName());
			assertNotNull(user.getLastName());
			assertNotNull(user.getStatus());
			assertNotNull(user.getCreatedAt());
			assertNotNull(user.getLastUpdatedAt());
			assertNotNull(userAddress.getId());
			assertNotNull(userAddress.getStreet());
			assertNotNull(userAddress.getCity());
			assertNotNull(userAddress.getProvince());
			assertNotNull(userAddress.getPostalCode());
			assertNotNull(userAddress.getCountryCode());
			assertNotNull(userAddress.getCreatedAt());
			assertNotNull(userAddress.getLastUpdatedAt());
		});
	}

	@Test
	void shouldFindUserByUsernameThrowsUserNotFoundException() {
		assertThrows(UserNotFoundException.class, () -> userService.getUserByEmailAddress("test"));
	}

	@Test
	void shouldFetchActiveUsersOneUser() {
		var pageableUsers = userService.fetchActiveUsers(PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 1);
		pageableUsers.stream().forEach(p -> {
			var userAddress = p.getAddress();
			assertAll(() -> {
				assertNotNull(p.getId());
				assertNotNull(p.getUsername());
				assertNotNull(p.getEmail());
				assertNotNull(p.getFirstName());
				assertNotNull(p.getLastName());
				assertNotNull(p.getStatus());
				assertNotNull(p.getCreatedAt());
				assertNotNull(p.getLastUpdatedAt());
				assertNotNull(userAddress.getId());
				assertNotNull(userAddress.getStreet());
				assertNotNull(userAddress.getCity());
				assertNotNull(userAddress.getProvince());
				assertNotNull(userAddress.getPostalCode());
				assertNotNull(userAddress.getCountryCode());
				assertNotNull(userAddress.getCreatedAt());
				assertNotNull(userAddress.getLastUpdatedAt());
			});
		});
	}

	@Test
	void shouldGetAllUsersReturnThree() {
		var pageableUsers = userService.getAllUsers(PageRequest.of(0, 100));
		assertEquals(pageableUsers.getTotalElements(), 3);
		pageableUsers.stream().forEach(p -> {
			var userAddress = p.getAddress();
			assertAll(() -> {
				assertNotNull(p.getId());
				assertNotNull(p.getUsername());
				assertNotNull(p.getEmail());
				assertNotNull(p.getFirstName());
				assertNotNull(p.getLastName());
				assertNotNull(p.getStatus());
				assertNotNull(p.getCreatedAt());
				assertNotNull(p.getLastUpdatedAt());
				assertNotNull(userAddress.getId());
				assertNotNull(userAddress.getStreet());
				assertNotNull(userAddress.getCity());
				assertNotNull(userAddress.getProvince());
				assertNotNull(userAddress.getPostalCode());
				assertNotNull(userAddress.getCountryCode());
				assertNotNull(userAddress.getCreatedAt());
				assertNotNull(userAddress.getLastUpdatedAt());
			});
		});
	}


}
