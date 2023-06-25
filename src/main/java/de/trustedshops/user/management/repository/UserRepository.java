package de.trustedshops.user.management.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.trustedshops.user.management.model.User;
import de.trustedshops.user.management.model.UserStatus;

public interface UserRepository extends JpaRepository<User, UUID> {

	Optional<User> findUserByUsername(String username);

	Optional<User> findUserByEmail(String email);

	Page<User> findUsersByUsernameContainingOrderByUsername(String username, Pageable pageable);

	Page<User> findUsersByStatus(UserStatus userStatus, Pageable pageable);

}
