package de.trustedshops.user.management.model;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.trustedshops.user.management.validation.CountryCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
@NamedEntityGraph(name = "graph.UserAddress.users", attributeNodes = @NamedAttributeNode("users"))
public class UserAddress extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotEmpty
    @Column(nullable = false)
    private String street;
    @NotEmpty
    @Column(nullable = false)
    private String city;
    @Column
    private String province;
    @NotNull
    @Size(min = 5, max = 5)
    @Column(nullable = false, length = 5)
    private String postalCode;

    @Column(nullable = false, name = "COUNTRY_CODE")
    @CountryCode()
    private String countryCode;
    @OneToMany(mappedBy = "userAddress", cascade = CascadeType.ALL)
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAddress that = (UserAddress) o;
        return Objects.equals(id, that.id) && Objects.equals(street, that.street) && Objects.equals(
                city,
                that.city
        ) && Objects.equals(province, that.province) && Objects.equals(postalCode, that.postalCode)
                && countryCode == that.countryCode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, street, city, province, postalCode, countryCode);
    }
}
