package com.dialltay.ehelper.users.port.in;

import com.dialltay.ehelper.users.domain.application.validation.BirthDatePermit;
import com.dialltay.ehelper.users.domain.model.Gender;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record CreateUserCommand(

        @NotNull(message = "{com.ehelper.user.gender.notnull}")
        Gender gender,

        @NotBlank(message = "{com.ehelper.user.firstname.notblank")
        @Size(min = 2, max = 50, message = "{com.ehelper.user.firstname.size}")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ '-]{2,75}$", message = "{com.ehelper.user.firstname.pattern}")
        String firstName,

        @NotBlank(message = "{com.ehelper.user.lastname.notblank")
        @Size(min = 2, max = 50, message = "{com.ehelper.user.lastname.size}")
        @Pattern(regexp = "^[A-Za-zÀ-ÖØ-öø-ÿ '-]{2,50}$", message = "{com.ehelper.user.lastname.pattern}")
        String lastName,

        @BirthDatePermit(minAge = 18, maxAge = 80, message = "{com.ehelper.user.birthdate.invalid}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd-MM-yyyy")
        LocalDate birthDate,

        @NotBlank(message = "{com.ehelper.user.email.notblank")
        @Email(message = "{com.ehelper.user.email.valid}")
        String email,

        @Pattern(regexp = "^\\+?[0-9 .-]{7,15}$", message = "{com.ehelper.user.telephone.pattern}")
        String telephone
) {
    public static CreateUserCommand defaultCommand() {
        return new CreateUserCommand(null,
                null,
                null,
                null,
                null,
                null
        );
    }
}
