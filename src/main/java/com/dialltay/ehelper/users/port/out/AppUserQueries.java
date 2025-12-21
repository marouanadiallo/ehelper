package com.dialltay.ehelper.users.port.out;

import com.dialltay.ehelper.users.domain.model.AppUser;

import java.util.Optional;

public interface AppUserQueries {
    Optional<AppUser> findById(Long id);

    boolean existsByEmailOrTelephone(String email, String telephone);
}
