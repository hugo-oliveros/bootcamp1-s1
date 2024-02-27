package com.pe.nttdata.s1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "check errores")
public class ServerException extends RuntimeException {

    private static final long serialVersionUID = 1L;
}