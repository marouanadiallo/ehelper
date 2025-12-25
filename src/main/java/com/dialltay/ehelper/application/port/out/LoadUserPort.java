package com.dialltay.ehelper.application.port.out;

import com.dialltay.ehelper.application.domain.model.UserProjections;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface LoadUserPort {
    boolean existsByEmailOrTelephone(String email, String telephone);
    Slice<UserProjections.UserTable> loadAUserSlice(Pageable pageable);
}
