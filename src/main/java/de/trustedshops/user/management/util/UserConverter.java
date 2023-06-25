package de.trustedshops.user.management.util;

import org.jetbrains.annotations.NotNull;

import de.trustedshops.user.management.dto.UserRequestDTO;
import de.trustedshops.user.management.dto.UserResponseDTO;
import de.trustedshops.user.management.dto.UserResponseDTO.ResponseUserAddressDTO;
import de.trustedshops.user.management.model.User;
import de.trustedshops.user.management.model.UserAddress;

public class UserConverter {

	@NotNull
	public static UserResponseDTO toUserResponseDTO(User user) {
		var userAddress = user.getUserAddress();
		return new UserResponseDTO(
				user.getId(),
				user.getUsername(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getStatus(),
				new ResponseUserAddressDTO(
						userAddress.getId(),
						userAddress.getStreet(),
						userAddress.getCity(),
						userAddress.getProvince(),
						userAddress.getPostalCode(),
						userAddress.getCountryCode(),
						userAddress.getCreatedAt(),
						userAddress.getLastUpdatedAt()
				),
				user.getCreatedAt(),
				user.getLastUpdatedAt()
		);
	}

	@NotNull
	public static User toUser(UserRequestDTO userRequestDTO) {
		var userAddress = userRequestDTO.getAddress();
		return User.builder()
				.username(userRequestDTO.getUsername())
				.firstName(userRequestDTO.getFirstName())
				.lastName(userRequestDTO.getLastName())
				.email(userRequestDTO.getEmail())
				.userAddress(
						UserAddress.builder()
								.street(userAddress.getStreet())
								.city(userAddress.getCity())
								.province(userAddress.getProvince())
								.postalCode(userAddress.getPostalCode())
								.countryCode(userAddress.getCountryCode())
								.build())
				.build();
	}

}
