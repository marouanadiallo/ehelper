package com.dialltay.ehelper.users.domain.application.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Objects;

public class BirthDateValidator implements ConstraintValidator<BirthDatePermit, LocalDate> {
    private BirthDatePermit birthDatePermitAnnotation;

    @Override
    public void initialize(BirthDatePermit constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.birthDatePermitAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        if (Objects.isNull(birthDate)) {
            return false;
        }
       LocalDate today = LocalDate.now();

        // Calculate age
        int age = today.getYear() - birthDate.getYear();
        return  age >= this.birthDatePermitAnnotation.minAge() && age <= this.birthDatePermitAnnotation.maxAge();
    }
}
