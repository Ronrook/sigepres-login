package com.sigepres.servicioweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {
    private int statusCode;
    private String message;
}

