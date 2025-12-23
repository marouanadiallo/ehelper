package com.dialltay.ehelper.users.adapter.out.persistence;

import com.dialltay.ehelper.users.domain.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    boolean existsByEmailOrTelephone(String email, String telephone);
}
