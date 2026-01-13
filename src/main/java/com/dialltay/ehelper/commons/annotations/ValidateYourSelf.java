package com.dialltay.ehelper.commons.annotations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.Set;

public interface ValidateYourSelf <T> {

    @SuppressWarnings("unchecked")
    default void validateSelf() {
        try(var validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> violations = validator.validate((T)this);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        }
    }
}
