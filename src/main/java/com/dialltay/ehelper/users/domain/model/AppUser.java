package com.dialltay.ehelper.users.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Getter
public class AppUser {

    private final Long id;
    private final UUID businessId;

    @Setter
    private Gender gender;

    @Setter
    private String firstName;

    @Setter
    private String lastName;

    @Setter
    private String email;

    @Setter
    private String telephone;

    @Setter
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    protected AppUser(Long id, UUID businessId) {
        this.id = id;
        this.businessId = businessId;
    }

    public static AppUser withId(Long id,
                                 UUID businessId,
                                 Gender gender,
                                 String firstName,
                                 String lastName,
                                 LocalDate birthDate,
                                 String email,
                                 String telephone) {

        AppUser user = new AppUser(id, businessId);
        user.gender = gender;
        user.firstName = firstName;
        user.lastName = lastName;
        user.birthDate = birthDate;

        user.email = email;
        user.telephone = telephone;
        return user;
    }

    public static AppUser of(Gender gender, String firstName, String lastName, LocalDate birthDate, String email, String telephone) {
        AppUser user = new AppUser(null, UUID.randomUUID());
        user.gender = gender;
        user.firstName = firstName;
        user.lastName = lastName;
        user.birthDate = birthDate;

        user.email = email;
        user.telephone = telephone;
        return user;
    }

    public Date getSqlBirthDate() {
        return Date.valueOf(birthDate.toString());
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof AppUser appUser)) return false;

        return getBusinessId().equals(appUser.getBusinessId());
    }

    @Override
    public int hashCode() {
        return getBusinessId().hashCode();
    }
}
