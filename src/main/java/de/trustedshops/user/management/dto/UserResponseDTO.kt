package de.trustedshops.user.management.dto

import com.fasterxml.jackson.annotation.JsonProperty
import de.trustedshops.user.management.model.UserStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

data class UserResponseDTO(
        @JsonProperty
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        var id: UUID,
        @JsonProperty
        var username: String,
        @JsonProperty
        var firstName: String,
        @JsonProperty
        var lastName: String,
        @JsonProperty
        var email: String,
        @JsonProperty
        var status: UserStatus,
        @JsonProperty
        var address: ResponseUserAddressDTO,
        @JsonProperty
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        var createdAt: Date,
        @JsonProperty
        @Schema(accessMode = Schema.AccessMode.READ_ONLY)
        var lastUpdatedAt: Date
) {
        data class ResponseUserAddressDTO(
                @JsonProperty
                @Schema(accessMode = Schema.AccessMode.READ_ONLY)
                var id: UUID,
                @JsonProperty
                var street: String,
                @JsonProperty
                var city: String,
                @JsonProperty
                var province: String,
                @JsonProperty
                var postalCode: String,
                @JsonProperty
                @Schema(description = "ISO 3166-1 alpha-2, exp: DE")
                var countryCode: String,
                @JsonProperty
                @Schema(accessMode = Schema.AccessMode.READ_ONLY)
                var createdAt: Date,
                @JsonProperty
                @Schema(accessMode = Schema.AccessMode.READ_ONLY)
                var lastUpdatedAt: Date
        )
}
