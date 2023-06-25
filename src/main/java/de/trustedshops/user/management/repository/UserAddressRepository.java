package de.trustedshops.user.management.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import de.trustedshops.user.management.model.UserAddress;

public interface UserAddressRepository extends JpaRepository<UserAddress, UUID> {

}
