package com.wagnerandrade.cursomc.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class DataIntregratyException extends RuntimeException {

    public DataIntregratyException(String message) {
        super(message);
    }

    public DataIntregratyException(String message, Throwable cause) {
        super(message, cause);
    }

}
