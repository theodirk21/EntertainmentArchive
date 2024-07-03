package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception;


import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException  {
    public NotFoundException(String e) {
        super(HttpStatus.NOT_FOUND, e);
    }
}
