package com.dialltay.ehelper.application.port.out;

import java.time.LocalDate;

public interface UpdateUserPort {
    void updateContactInfo(Long id,  String email, String telephone);
    void updatePersonalInfo(Long id, String firstName, String lastName, LocalDate birthDate);
}
