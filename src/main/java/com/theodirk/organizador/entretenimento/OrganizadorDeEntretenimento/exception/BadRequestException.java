package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception;


import org.springframework.http.HttpStatus;

import java.util.List;

public class BadRequestException extends ApplicationException  {
    public BadRequestException(List<String> detail) {
        super(HttpStatus.BAD_REQUEST, "Bad Request", detail);
    }
}
