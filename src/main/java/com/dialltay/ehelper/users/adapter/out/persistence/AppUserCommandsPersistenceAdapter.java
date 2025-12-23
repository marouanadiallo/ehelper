package com.dialltay.ehelper.users.adapter.out.persistence;

import com.dialltay.ehelper.commons.annotations.PersistenceAdapter;
import com.dialltay.ehelper.users.domain.model.AppUser;
import com.dialltay.ehelper.users.port.out.AppUserCommands;

import java.time.LocalDate;
import java.util.List;

@PersistenceAdapter
public class AppUserCommandsPersistenceAdapter implements AppUserCommands {
    private final AppUserRepository userRepository;

    public AppUserCommandsPersistenceAdapter(AppUserRepository appUserRepository) {
        this.userRepository = appUserRepository;
    }

    @Override
    public Long save(AppUser user) {
        return this.userRepository.save(user).getId();
    }

    @Override
    public void saveAll(List<AppUser> users) {
        this.userRepository.saveAll(users);
    }

    @Override
    public void updateContactInfo(Long id, String email, String telephone) {

    }

    @Override
    public void updatePersonalInfo(Long id, String firstName, String lastName, LocalDate birthDate) {

    }
}
