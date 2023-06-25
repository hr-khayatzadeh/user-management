package de.trustedshops.user.management.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

data class UserRequestDTO(
        @JsonProperty(required = true)
        var username: String,
        @JsonProperty(required = true)
        var firstName: String,
        @JsonProperty(required = true)
        var lastName: String,
        @JsonProperty(required = true)
        var email: String,
        @JsonProperty(required = true)
        var address: RequestUserAddressDTO,
) {
    data class RequestUserAddressDTO(
            @JsonProperty(required = true)
            var street: String,
            @JsonProperty(required = true)
            var city: String,
            @JsonProperty(required = true)
            @Parameter(required = false)
            var province: String,
            @JsonProperty(required = true)
            @Size(min = 5, max = 5)
            var postalCode: String,
            @JsonProperty(required = true)
            @Schema(description = "ISO 3166-1 alpha-2, exp: DE")
            var countryCode: String,
    )
}
