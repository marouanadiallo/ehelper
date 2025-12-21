package com.dialltay.ehelper.users.domain.application.exception;

public class UserDuplicationException extends RuntimeException {
    public UserDuplicationException(String message) {
        super(message);
    }
}
