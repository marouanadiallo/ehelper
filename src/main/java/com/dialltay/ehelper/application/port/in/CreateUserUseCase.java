package com.dialltay.ehelper.application.port.in;

import java.util.List;

public interface CreateUserUseCase {
    Long createUser(CreateUserCommand command);
    void createUsersBulk(List<CreateUserCommand> commands);
}
