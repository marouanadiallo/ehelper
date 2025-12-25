package com.dialltay.ehelper.adapter.conversion;

import com.dialltay.ehelper.application.domain.model.Gender;
import org.jspecify.annotations.NonNull;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;


public class GenderConverter implements Formatter<Gender> {

    @NonNull
    @Override
    public Gender parse(@NonNull String genderText, @NonNull Locale locale) throws ParseException {
        return switch (genderText) {
          case String gr when "Homme".equalsIgnoreCase(gr) || "H".equalsIgnoreCase(gr) -> Gender.HOMME;
          case String gr when "Femme".equalsIgnoreCase(gr) || "F".equalsIgnoreCase(gr) -> Gender.FEMME;
            default -> throw new IllegalArgumentException("Gender unknown");
        };
    }

    @NonNull
    @Override
    public String print(@NonNull Gender object, @NonNull Locale locale) {
        return Gender.FEMME.getGender();
    }
}
