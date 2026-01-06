package com.dialltay.ehelper.application.port.in;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface CreateUserUseCase {
    Long createUser(CreateUserCommand command);
    void createUsersBatch(MultipartFile file);
}
