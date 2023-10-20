package com.sigepres.servicioweb.exceptions;

import com.sigepres.servicioweb.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDto>> validateError(MethodArgumentNotValidException ex){

        List<ErrorDto> errorDtos = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDto(400, fieldError.getDefaultMessage()))
                .distinct()
                .toList();

        return new ResponseEntity<>(errorDtos, HttpStatus.BAD_REQUEST);
    }
}
