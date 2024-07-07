package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.ErroResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleConstrainViolationException(ConstraintViolationException e){
        List<String> constraintsViolated = e
                .getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(BAD_REQUEST.getReasonPhrase())
                .details(constraintsViolated)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleApplicationException(ApplicationException e){

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(e.getMessage())
                .details(e.getDetails())
                .build();

        return ResponseEntity.status(e.getStatusCode()).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<String> constraintsViolated = e
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + "-" + error.getDefaultMessage())
                .toList();

        constraintsViolated.addAll(
                e.getBindingResult()
                        .getGlobalErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage).toList()
        );

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(BAD_REQUEST.getReasonPhrase())
                .details(constraintsViolated)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException e){

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(BAD_REQUEST.getReasonPhrase())
                .details(Optional.of(e.getMessage())
                        .map(Collections::singletonList)
                        .orElseGet(Collections::emptyList))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){

        String message = "Required request body is missing";

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(BAD_REQUEST.getReasonPhrase())
                .details(List.of(message))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public ResponseEntity<ErroResponseDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){

        ErroResponseDTO err = ErroResponseDTO.builder()
                .description(BAD_REQUEST.getReasonPhrase())
                .details(e.getMessage() != null
                        ? Collections.singletonList(e.getMessage())
                        : Collections.emptyList())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }


}
