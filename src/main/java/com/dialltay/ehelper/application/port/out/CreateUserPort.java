package com.dialltay.ehelper.application.port.out;

import com.dialltay.ehelper.application.port.in.CreateUserCommand;

import java.util.List;

public interface CreateUserPort {
    Long save(CreateUserCommand command);
    void saveAll(List<CreateUserCommand> commands);
}
