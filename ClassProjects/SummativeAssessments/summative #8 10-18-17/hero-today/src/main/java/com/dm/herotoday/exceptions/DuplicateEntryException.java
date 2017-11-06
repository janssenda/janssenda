package com.dm.herotoday.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Duplicate")
public class DuplicateEntryException extends Exception {
    public DuplicateEntryException (String message) {
        super(message);
    }
}
