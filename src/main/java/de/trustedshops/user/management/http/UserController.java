package de.trustedshops.user.management.http;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.trustedshops.user.management.dto.UserRequestDTO;
import de.trustedshops.user.management.dto.UserResponseDTO;
import de.trustedshops.user.management.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user-management")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Operation(summary = "Search users by username")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			})
	})
	@GetMapping(value = "/users/search/{username}")
	public Page<UserResponseDTO> searchUsersByUsername(@PathVariable final String username,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "100") int size) {
		return userService.searchUsersByUsername(username, PageRequest.of(page, size));
	}

	@Operation(summary = "Fetch activated users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			})
	})
	@GetMapping(value = "/users/active")
	public Page<UserResponseDTO> fetchActiveUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
		return userService.fetchActiveUsers(PageRequest.of(page, size));
	}

	@Operation(summary = "Fetch user by email address")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			})
	})
	@GetMapping(value = "/user/email/{emailAddress}")
	public UserResponseDTO fetchUserByEmailAddress(@PathVariable String emailAddress) {
		return userService.getUserByEmailAddress(emailAddress);
	}

	@Operation(summary = "Fetch user by username")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			})
	})
	@GetMapping(value = "/user/username/{username}")
	public UserResponseDTO fetchUserByUsernameAddress(@PathVariable String username) {
		return userService.getUserByUsername(username);
	}

	@Operation(summary = "Fetch all users")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			})
	})
	@GetMapping(value = "/users")
	public Page<UserResponseDTO> getAllUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {
		return userService.getAllUsers(PageRequest.of(page, size));
	}

	@Operation(summary = "Add a user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "The user is added", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			}),
			@ApiResponse(responseCode = "409", description = "If user with provided username or email address already exists")
	})
	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		var userResponseDTO = userService.addUser(userRequestDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
	}

	@Operation(summary = "Activate user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "The user activated", content = {
					@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = UserResponseDTO.class))
			}),
			@ApiResponse(responseCode = "404", description = "User not found")
	})
	@PatchMapping(value = "/user/active", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseDTO> activateUser(@RequestParam(name = "username") String username) {
		var userResponseDTO = userService.activateUser(username);
		return ResponseEntity.ok(userResponseDTO);
	}

}
