package com.dialltay.ehelper.users.port.out;

import com.dialltay.ehelper.users.domain.model.AppUser;

import java.time.LocalDate;
import java.util.List;

public interface AppUserCommands {
    Long save(AppUser user);
    void saveAll(List<AppUser> users);


    void updateContactInfo(Long id,  String email, String telephone);
    void updatePersonalInfo(Long id, String firstName, String lastName, LocalDate birthDate);
}
