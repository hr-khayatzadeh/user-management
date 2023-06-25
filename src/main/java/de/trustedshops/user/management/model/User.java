package de.trustedshops.user.management.model;

import java.util.Objects;
import java.util.UUID;

import de.trustedshops.user.management.validation.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "graph.User", attributeNodes = {
        @NamedAttributeNode("userAddress")})
@Valid
public class User extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotEmpty
    @Column(name = "USERNAME", nullable = false, unique = true)
    @Username()
    private String username;
    @Email
    @NotEmpty
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;
    @NotEmpty
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @NotEmpty
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "USER_ADDRESS_ID")
    @Valid
    private UserAddress userAddress;
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(
                firstName,
                user.firstName
        ) && Objects.equals(lastName, user.lastName) && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, status);
    }
}
