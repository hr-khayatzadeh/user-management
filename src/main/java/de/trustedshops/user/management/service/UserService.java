package de.trustedshops.user.management.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import de.trustedshops.user.management.dto.UserRequestDTO;
import de.trustedshops.user.management.dto.UserResponseDTO;
import de.trustedshops.user.management.exception.DuplicateUserException;
import de.trustedshops.user.management.exception.UserNotFoundException;

public interface UserService {
	Page<UserResponseDTO> searchUsersByUsername(String username, Pageable pageable);

	Page<UserResponseDTO> fetchActiveUsers(Pageable pageable);

	Page<UserResponseDTO> getAllUsers(Pageable pageable);

	UserResponseDTO getUserByUsername(String username) throws UserNotFoundException;

	UserResponseDTO getUserByEmailAddress(String email) throws UserNotFoundException;

	UserResponseDTO addUser(UserRequestDTO userRequestDTO) throws DuplicateUserException;

	UserResponseDTO activateUser(String username) throws UserNotFoundException;

}
