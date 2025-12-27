package com.dialltay.ehelper.adapter.out.persistence;

import com.dialltay.ehelper.application.domain.model.UserProjections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUserJpaEntity, Long> {

    boolean existsByEmailOrTelephone(String email, String telephone);

    @Query("""
            Select u.id as id,
                   u.firstName as firstName,
                   u.lastName as lastName,
                   u.email as email,
                   u.telephone as telephone
            from AppUser u
            """)
    Slice<UserProjections.UserTable> findAllBy(Pageable pageable);
}
