package com.dialltay.ehelper.application.domain.model;

public interface UserProjections {

    /**
     * Projection for listing users with essential details.
     */
     record UserTable(
            Long id,
            String firstName,
            String lastName,
            String email,
            String telephone){}
}
