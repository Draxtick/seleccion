package com.globallogic.usuarios.exceptions;

import com.globallogic.usuarios.utils.Constants;
import com.globallogic.usuarios.dto.ErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionManager extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DuplicateUserException.class)
    protected ResponseEntity<Object> handleUserException(
            DuplicateUserException ex, WebRequest request) {
        ErrorDTO errorDTO = ErrorDTO.builder().message(Constants.ERROR001.value()).build();
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DuplicateEmailException.class)
    protected ResponseEntity<Object> handleEmailException(
            DuplicateEmailException ex, WebRequest request) {
        ErrorDTO errorDTO = ErrorDTO.builder().message(Constants.ERROR002.value()).build();
        return new ResponseEntity<>(errorDTO,HttpStatus.BAD_REQUEST);

    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

 }

