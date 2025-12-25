package com.dialltay.ehelper.application.domain.model;

import lombok.Getter;

import java.util.Objects;

@Getter
public enum Gender {
    HOMME("Homme"),
    FEMME("Femme");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public static boolean isValidGender(String genderStr) {
        if (Objects.isNull(genderStr)) return  false;

        try {
            if ("H".equalsIgnoreCase(genderStr)) {
                genderStr = "Homme";
            } else if ("F".equalsIgnoreCase(genderStr)) {
                genderStr = "Femme";
            }
            Gender.valueOf(genderStr);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
