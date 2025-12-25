package com.dialltay.ehelper.application.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Constraint(validatedBy = BirthDateValidator.class)
@Documented
public @interface BirthDatePermit {

    String message() default "com.ehelper.user.birthdate.invalid";

    int minAge() default 0;
    int maxAge() default 100;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
