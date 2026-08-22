package com.tokio.filme.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.EXPECTATION_FAILED)
public class SaveException extends RuntimeException {

    public SaveException(String message){
        super(message);
    }
}
