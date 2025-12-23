package com.dialltay.ehelper.users.port.out;

public interface AppUserQueries {
    boolean existsByEmailOrTelephone(String email, String telephone);
}
