package de.trustedshops.user.management.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(enumAsRef = true)
public enum UserStatus {
    ENABLED,
    DISABLED,
    REQUIRED_CHANGE_PASSWORD
}
