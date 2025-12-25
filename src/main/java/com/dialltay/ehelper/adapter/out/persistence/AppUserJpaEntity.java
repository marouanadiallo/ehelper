package com.dialltay.ehelper.adapter.out.persistence;

import com.dialltay.ehelper.application.domain.model.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity(name = "AppUser")
@Table(name = "t_app_user", uniqueConstraints = {
        @UniqueConstraint(name = "uq_user_business_id", columnNames = "business_id"),
        @UniqueConstraint(name = "uq_user_email", columnNames = "email"),
        @UniqueConstraint(name = "uq_user_telephone", columnNames = "telephone")
})
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class AppUserJpaEntity extends BaseJpaEntity<Long> {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_user_seq_gen"
    )
    @SequenceGenerator(
            name = "app_user_seq_gen",
            sequenceName = "seq_app_user",
            allocationSize = 75
    )
    @Column(name = "id", updatable = false, nullable = false)
    private final Long id;

    @Column(name = "business_id", nullable = false, updatable = false)
    private final UUID businessId;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false, length = 5)
    private final Gender gender;

    @Column(name = "first_name", nullable = false, length = 100)
    private final String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private final String lastName;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private final String email;

    @Column(name = "telephone", nullable = false, length = 15)
    private final String telephone;

    @Column(name = "birth_date", nullable = false)
    private final LocalDate birthDate;

    @Column(name = "telephone_checked", nullable = false)
    private final boolean telephoneChecked = false;

    @Column(name = "account_non_locked", nullable = false)
    private final boolean accountNonLocked = true;

    @Column(name = "enabled", nullable = false)
    private final boolean enabled = false;

    @Embedded
    private final AuditMetadata auditMetadata = AuditMetadata.defaultMetadata();

    public AppUserJpaEntity withId(Long id ) {
        return new AppUserJpaEntity(id,
                this.businessId,
                this.gender,
                this.firstName,
                this.lastName,
                this.email,
                this.telephone,
                this.birthDate
        );
    }

    public static AppUserJpaEntity of(Gender gender,
                                      String firstName,
                                      String lastName,
                                      LocalDate birthDate,
                                      String email,
                                      String telephone) {
        return new AppUserJpaEntity(
                null,
                UUID.randomUUID(),
                gender,
                firstName,
                lastName,
                email,
                telephone,
                birthDate
        );
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AppUserJpaEntity appUser)) return false;

        return getBusinessId().equals(appUser.getBusinessId());
    }

    @Override
    public int hashCode() {
        return getBusinessId().hashCode();
    }
}
