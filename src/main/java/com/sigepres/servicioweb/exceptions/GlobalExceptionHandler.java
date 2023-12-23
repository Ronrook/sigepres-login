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

    @ExceptionHandler(DuplicateValueException.class)
    public ResponseEntity<ErrorDto> duplicateFieldException(Exception e){
        ErrorDto errorDto = new ErrorDto(400, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> userNotFoundException(Exception e){
        ErrorDto errorDto = new ErrorDto(404, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PasswordIncorrectException.class)
    public ResponseEntity<ErrorDto> passwordIncorrectException(Exception e){
        ErrorDto errorDto = new ErrorDto(404, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> NotFoundException(Exception e){
        ErrorDto errorDto = new ErrorDto(404, e.getMessage());
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    /*@ExceptionHandler(PasswordIncorrectException.class)
    public ProblemDetail passwordIncorrectException(Exception e){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }*/
}
