package com.dialltay.ehelper.application.port.in;

import org.springframework.web.multipart.MultipartFile;


public interface CreateUserUseCase {
    Long createUser(CreateUserCommand command);
    void createUsersBatch(MultipartFile file);
}
