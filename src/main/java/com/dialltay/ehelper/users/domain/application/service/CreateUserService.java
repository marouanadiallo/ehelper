package com.dialltay.ehelper.users.domain.application.service;

import com.dialltay.ehelper.users.domain.application.exception.UserDuplicationException;
import com.dialltay.ehelper.users.domain.model.AppUser;
import com.dialltay.ehelper.users.port.in.CreateUserCommand;
import com.dialltay.ehelper.users.port.in.CreateUserUseCase;
import com.dialltay.ehelper.users.port.out.AppUserCommands;
import com.dialltay.ehelper.users.port.out.AppUserQueries;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateUserUseCase {

    private final AppUserCommands userCommands;
    private final AppUserQueries userQueries;

    public CreateUserService(AppUserCommands userCommands, AppUserQueries userQueries) {
        this.userCommands = userCommands;
        this.userQueries = userQueries;
    }

    @Override
    public Long createUser(CreateUserCommand command) {
        var exists = userQueries.existsByEmailOrTelephone(command.email(), command.telephone());
        if (exists) {
            throw new UserDuplicationException("Duplication d'utilisateur, email ou téléphone existe déjà.");
        }

        return this.userCommands.save(
                AppUser.of(
                        command.gender(),
                        command.firstName(),
                        command.lastName(),
                        command.birthDate(),
                        command.email(),
                        command.telephone()
                )
        );
    }

}
