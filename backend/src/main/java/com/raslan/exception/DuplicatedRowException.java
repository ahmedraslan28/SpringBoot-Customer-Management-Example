package com.raslan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedRowException extends RuntimeException {
    public DuplicatedRowException(String message){
        super(message);
    }
}
