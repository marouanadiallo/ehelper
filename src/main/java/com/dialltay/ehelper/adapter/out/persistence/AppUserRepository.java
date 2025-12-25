package com.dialltay.ehelper.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUserJpaEntity, Long> {

    boolean existsByEmailOrTelephone(String email, String telephone);
}
