package de.trustedshops.user.management.http;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.trustedshops.user.management.dto.UserRequestDTO;
import de.trustedshops.user.management.dto.UserRequestDTO.RequestUserAddressDTO;
import de.trustedshops.user.management.dto.UserResponseDTO;
import de.trustedshops.user.management.dto.UserResponseDTO.ResponseUserAddressDTO;
import de.trustedshops.user.management.model.UserStatus;
import de.trustedshops.user.management.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	public static final String USERNAME = "hr.khayatzadeh";
	public static final String FIRST_NAME = "Hamidreza";
	public static final String LAST_NAME = "Khayatzadeh";
	public static final String EMAIL = "hr.khayatzadeh@gmail.com";
	public static final String STREET = "Am Wachberg";
	public static final String CITY = "Erftstadt";
	public static final String PROVINCE = "NRW";
	public static final String POSTAL_CODE = "50374";
	public static final String COUNTRY_CODE = "DE";
	@InjectMocks
	UserController userController;

	@Mock
	UserService userService;

	@BeforeEach
	void init() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		lenient().when(userService.addUser(any())).thenReturn(
				getUserResponseDTO());
	}

	@NotNull
	private UserResponseDTO getUserResponseDTO() {
		var userAddressDTO = new ResponseUserAddressDTO(UUID.randomUUID(), STREET,
				CITY, PROVINCE, POSTAL_CODE, COUNTRY_CODE, now(), now()
		);
		return new UserResponseDTO(
				UUID.randomUUID(),
				USERNAME,
				FIRST_NAME,
				LAST_NAME,
				EMAIL,
				UserStatus.REQUIRED_CHANGE_PASSWORD,
				userAddressDTO, now(), now()
		);
	}


	@Test
	void addUser() {
		var responseEntity = userController.addUser(new UserRequestDTO(USERNAME, FIRST_NAME, LAST_NAME, EMAIL,
				new RequestUserAddressDTO(STREET, CITY, PROVINCE, POSTAL_CODE, COUNTRY_CODE)));
		assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void activateUser() {
		when(userService.activateUser(any())).thenReturn(getUserResponseDTO());
		var responseEntity =
				userController.activateUser(USERNAME);
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertNotNull(responseEntity.getBody());
	}

	@Test
	void searchUsersByUsername() {
		when(userService.searchUsersByUsername(USERNAME, PageRequest.of(0, 100))).thenReturn(new PageImpl<>(List.of(getUserResponseDTO())));
		var pageableUsersDTO = userController.searchUsersByUsername(USERNAME, 0, 100);
		assertFalse(pageableUsersDTO.isEmpty());
	}
}
