package com.dm.herotoday.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Duplicate")
public class SQLUpdateException extends Exception {
    public SQLUpdateException(String message) {
        super(message);
    }
}
