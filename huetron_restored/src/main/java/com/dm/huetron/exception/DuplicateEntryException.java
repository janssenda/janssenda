package com.dm.huetron.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate")
public class DuplicateEntryException
        extends Exception {
    public DuplicateEntryException(String message) {
        super(message);
    }
}
