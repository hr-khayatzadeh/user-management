package de.trustedshops.user.management.service;

import static de.trustedshops.user.management.util.UserConverter.toUserResponseDTO;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import de.trustedshops.user.management.dto.UserRequestDTO;
import de.trustedshops.user.management.dto.UserResponseDTO;
import de.trustedshops.user.management.exception.DuplicateUserException;
import de.trustedshops.user.management.exception.UserNotFoundException;
import de.trustedshops.user.management.model.User;
import de.trustedshops.user.management.model.UserStatus;
import de.trustedshops.user.management.repository.UserAddressRepository;
import de.trustedshops.user.management.repository.UserRepository;
import de.trustedshops.user.management.util.UserConverter;

@Component
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserAddressRepository userAddressRepository;

	public UserServiceImpl(
			UserRepository userRepository,
			UserAddressRepository userAddressRepository
	) {
		this.userRepository = userRepository;
		this.userAddressRepository = userAddressRepository;
	}

	@Override
	@NotNull
	public Page<UserResponseDTO> searchUsersByUsername(@NotNull final String username, @NotNull final Pageable pageable) {
		Page<User> pageableUsers = userRepository.findUsersByUsernameContainingOrderByUsername(
				username,
				pageable
		);
		var usersResponse = pageableUsers
				.stream().map(UserConverter::toUserResponseDTO).toList();
		return toPageable(pageableUsers, usersResponse);
	}

	@Override
	@NotNull
	public Page<UserResponseDTO> fetchActiveUsers(@NotNull final Pageable pageable) {
		Page<User> pageableUsers = userRepository.findUsersByStatus(UserStatus.ENABLED, pageable);
		var usersResponse = pageableUsers
				.stream().map(UserConverter::toUserResponseDTO).toList();
		return toPageable(pageableUsers, usersResponse);
	}

	@NotNull
	private static PageImpl<UserResponseDTO> toPageable(@NotNull final Page<User> pageableUsers, @NotNull final List<UserResponseDTO> usersResponse) {
		return new PageImpl<>(usersResponse, PageRequest.of(
				pageableUsers.getNumber(),
				pageableUsers.getSize(),
				pageableUsers.getSort()
		),
				pageableUsers.getTotalPages()
		);
	}

	@Override
	@NotNull
	public Page<UserResponseDTO> getAllUsers(@NotNull final Pageable pageable) {
		Page<User> pageableUsers = userRepository.findAll(pageable);
		var usersResponse = pageableUsers
				.stream().map(UserConverter::toUserResponseDTO).toList();
		return toPageable(pageableUsers, usersResponse);
	}

	@Override
	@NotNull
	public UserResponseDTO getUserByUsername(@NotNull final String username) throws UserNotFoundException {
		return userRepository.findUserByUsername(username)
				.map(UserConverter::toUserResponseDTO)
				.orElseThrow(UserNotFoundException::new);
	}

	@Override
	@NotNull
	public UserResponseDTO getUserByEmailAddress(@NotNull final String email) throws UserNotFoundException {
		return userRepository.findUserByEmail(email)
				.map(UserConverter::toUserResponseDTO)
				.orElseThrow(UserNotFoundException::new);
	}

	@Override
	@NotNull
	public UserResponseDTO addUser(@NotNull final UserRequestDTO userRequestDTO) throws DuplicateUserException {
		if (userRepository.findUserByUsername(userRequestDTO.getUsername()).isPresent()) {
			throw new DuplicateUserException(String.format(
					"There is already a user with username %s",
					userRequestDTO.getUsername()
			));
		}
		if (userRepository.findUserByEmail(userRequestDTO.getEmail()).isPresent()) {
			throw new DuplicateUserException(String.format(
					"There is already a user with email address %s",
					userRequestDTO.getEmail()
			));
		}
		var user = UserConverter.toUser(userRequestDTO);
		user.setStatus(UserStatus.REQUIRED_CHANGE_PASSWORD);
		userAddressRepository.save(user.getUserAddress());
		userRepository.save(user);
		return toUserResponseDTO(user);
	}

	@Override
	@NotNull
	public UserResponseDTO activateUser(@NotNull final String username) throws UserNotFoundException {
		var user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
		user.setStatus(UserStatus.ENABLED);
		userRepository.save(user);
		return toUserResponseDTO(user);
	}
}
