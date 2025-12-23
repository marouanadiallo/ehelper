package com.dialltay.ehelper.users.adapter.out.persistence;

import com.dialltay.ehelper.commons.annotations.PersistenceAdapter;
import com.dialltay.ehelper.users.port.out.AppUserQueries;

@PersistenceAdapter
public class AppUserQueriesPersistenceAdapter implements AppUserQueries {

    private final AppUserRepository userRepository;

    public AppUserQueriesPersistenceAdapter(AppUserRepository appUserRepository) {
        this.userRepository = appUserRepository;
    }

    @Override
    public boolean existsByEmailOrTelephone(String email, String telephone) {
        return this.userRepository.existsByEmailOrTelephone(email, telephone);
    }

}
