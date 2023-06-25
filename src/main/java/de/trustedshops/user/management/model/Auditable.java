package de.trustedshops.user.management.model;

import static jakarta.persistence.TemporalType.TIMESTAMP;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable {
    @Column(name = "CREATED_AT", updatable = false)
    @Temporal(TIMESTAMP)
    @CreatedDate
    protected Date createdAt;

    @Column(name = "LAST_UPDATED_AT")
    @LastModifiedDate
    @Temporal(TIMESTAMP)
    protected Date lastUpdatedAt;

    @Version
    protected Integer version;

}
