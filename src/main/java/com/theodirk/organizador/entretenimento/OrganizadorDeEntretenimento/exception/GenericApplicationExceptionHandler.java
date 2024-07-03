package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.ErroResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Order
@ControllerAdvice
public class GenericApplicationExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleException(Exception e){
        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);

    }
}
