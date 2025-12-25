package com.dialltay.ehelper.application.domain.service;

import com.dialltay.ehelper.application.port.in.CreateUserCommand;
import com.dialltay.ehelper.application.port.in.CreateUserUseCase;
import com.dialltay.ehelper.application.port.out.CreateUserPort;
import com.dialltay.ehelper.application.port.out.LoadUserPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateUserService implements CreateUserUseCase {

    private final CreateUserPort userCommands;
    private final LoadUserPort userQueries;

    public CreateUserService(CreateUserPort userCommands, LoadUserPort userQueries) {
        this.userCommands = userCommands;
        this.userQueries = userQueries;
    }

    @Override
    @Transactional
    public Long createUser(CreateUserCommand command) {
        var exists = userQueries.existsByEmailOrTelephone(command.email(), command.telephone());
        if (exists) {
            throw new UserDuplicationException("Duplication d'utilisateur, email ou téléphone existe déjà.");
        }

        // notify your about his/her account creation
        return this.userCommands.save(command);
    }

    @Override
    public void createUsersBulk(List<CreateUserCommand> commands) {

    }

}
