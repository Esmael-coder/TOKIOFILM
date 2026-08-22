package com.tokio.filme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class UsernameOrEmailExistException extends RuntimeException {
    public UsernameOrEmailExistException(String message) {
        super(message);
    }
}
